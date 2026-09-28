package com.example.usersm.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.usersm.Entity.AuditLog;
import com.example.usersm.Entity.Category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
// import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.usersm.Dto.ProductCacheDto;
import com.example.usersm.Dto.ProductRequestDto;
import com.example.usersm.Dto.ProductResponseDto;
import com.example.usersm.Entity.Product;
import com.example.usersm.Exception.CategoryNotFoundException;
import com.example.usersm.Exception.ProductNotFoundException;
import com.example.usersm.Repository.AuditRepository;
import com.example.usersm.Repository.CategoryRepositry;
import com.example.usersm.Repository.ProductRepositry;
import com.example.usersm.controller.CategoryController;
import com.example.usersm.controller.ProductController;
import com.example.usersm.service.ProductCacheService;
import com.example.usersm.service.ProductService;
import com.example.usersm.specification.ProductSpecification;

@Service
public class ProductServiceImpl implements ProductService {

        private final ProductRepositry productRepositry;
        private final CategoryRepositry categoryRepositry;
        private final ProductCacheService productCacheService;
        private final AuditRepository auditRepository;

        public ProductServiceImpl(ProductRepositry productRepositry, CategoryRepositry categoryRepositry,
                        ProductCacheService productCacheService, AuditRepository auditRepository) {
                this.productRepositry = productRepositry;
                this.categoryRepositry = categoryRepositry;
                this.productCacheService = productCacheService;
                this.auditRepository = auditRepository;
        }

        public static String uploadFile(MultipartFile file) throws IOException {
                if (file == null || file.isEmpty()) {
                        return null;
                }
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get("upload", fileName);

                if (!Files.exists(path.getParent())) {
                        Files.createDirectories(path.getParent());
                }

                Files.copy(file.getInputStream(), path);
                return fileName;
        }

        // Helper method to safely convert Entity to Dto without serialization loops
        private ProductResponseDto mapToResponseDto(Product product) {

                Long catId = null;
                String catName = null;

                if (product.getCategory() != null) {
                        catId = product.getCategory().getId();
                        catName = product.getCategory().getName();
                }

                ProductResponseDto dto = new ProductResponseDto(
                                product.getId(),
                                product.getName(),
                                product.getPrice(),
                                product.getDescription(),
                                product.getShort_description(),
                                product.getImage_url(),
                                product.getRating(),
                                catId,
                                catName);

                dto.add(
                                linkTo(
                                                methodOn(ProductController.class)
                                                                .getProductByid(product.getId()))
                                                .withSelfRel());

                dto.add(
                                linkTo(

                                                methodOn(ProductController.class)
                                                                .getAllProduct(0, 10, "id", "asc", null, null, null,
                                                                                null, null, null))
                                                .withRel("/get-product"));

                if (catId != null) {
                        dto.add(
                                        linkTo(
                                                        methodOn(CategoryController.class)
                                                                        .getCategoryByid(catId))
                                                        .withRel("/get-byid/{id}"));
                }

                return dto;
        }

        private ProductResponseDto mapCacheDtoToResponseDto(ProductCacheDto dto) {

                ProductResponseDto response = new ProductResponseDto(
                                dto.getId(),
                                dto.getName(),
                                dto.getPrice(),
                                dto.getDescription(),
                                dto.getShortDescription(),
                                dto.getImageUrl(),
                                dto.getRating(),
                                dto.getCategoryId(),
                                dto.getCategoryName());

                response.add(
                                linkTo(
                                                methodOn(ProductController.class)
                                                                .getProductByid(dto.getId()))
                                                .withSelfRel());

                response.add(
                                linkTo(
                                                methodOn(ProductController.class)
                                                                .getAllProduct(0, 10, "id", "asc",
                                                                                null, null, null, null, null, null))
                                                .withRel("products"));

                if (dto.getCategoryId() != null) {

                        response.add(
                                        linkTo(
                                                        methodOn(CategoryController.class)
                                                                        .getCategoryByid(dto.getCategoryId()))
                                                        .withRel("category"));
                }

                return response;
        }

        @Transactional
        @Override
        public ProductResponseDto createProduct(ProductRequestDto dto) throws IOException {
                if (dto.getCategoryId() == null) {
                        throw new IllegalArgumentException("Category ID must not be null");
                }
                Category category = categoryRepositry.findById(dto.getCategoryId())
                                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

                Product product = new Product();
                String fileName = uploadFile(dto.getImage_url());
                product.setName(dto.getName());
                product.setPrice(dto.getPrice());
                product.setDescription(dto.getDescription());
                product.setShort_description(dto.getShort_description());
                product.setImage_url(fileName);
                product.setRating(dto.getRating());
                product.setCategory(category);

                Product saveProduct = productRepositry.save(product);
                AuditLog log = new AuditLog();

                log.setAction("CREATE");
// log addeds
                log.setEntityName(saveProduct.getName());

                log.setCreatedAt(LocalDateTime.now());

                auditRepository.save(log);
                // throw new RuntimeException("Testing Rollback");
                return mapToResponseDto(saveProduct);
        }

        @Override

        public Page<ProductResponseDto> getAllProduct(int page, int size, String sortBy, String direction,
                        Long categoryId,
                        Double minPrice,
                        Double maxPrice,
                        String name, Double minRating, Double maxRating) {

                Sort sort = direction.equalsIgnoreCase("desc")
                                ? Sort.by(sortBy).descending()
                                : Sort.by(sortBy).ascending();
                Pageable pageable = PageRequest.of(page, size, sort);
                Specification<Product> specification = (root, query, cb) -> cb.conjunction();

                if (categoryId != null) {
                        specification = specification.and(
                                        ProductSpecification.hasCategory(categoryId));
                }

                if (minPrice != null) {
                        specification = specification.and(
                                        ProductSpecification.hasMinPrice(minPrice));
                }

                if (maxPrice != null) {
                        specification = specification.and(
                                        ProductSpecification.hasMaxPrice(maxPrice));
                }

                if (name != null && !name.isBlank()) {
                        specification = specification.and(
                                        ProductSpecification.hasName(name));
                }
                if (minRating != null) {
                        specification = specification.and(
                                        ProductSpecification.hasMinRating(minRating));
                }

                if (maxRating != null) {
                        specification = specification.and(
                                        ProductSpecification.hasMaxRating(maxRating));
                }

                return productRepositry.findAll(specification, pageable)
                                .map(this::mapToResponseDto);
        }

        // ===========================
        // Cache Entity
        // ===========================

        // ===========================
        // Controller calls this method
        // ===========================
        @Transactional
        @Override
        public ProductResponseDto getProductByid(long id) {
                
                ProductCacheDto cacheDto = productCacheService.getProduct(id);
                AuditLog log = new AuditLog();
                log.setAction("DATA FETCHED");
                log.setEntityName(cacheDto.getName());
                log.setCreatedAt(LocalDateTime.now());
                auditRepository.save(log);
                
                return mapCacheDtoToResponseDto(cacheDto);
        }

        @Override
        public void deleteProduct(long id) {
                Product product = productRepositry.findById(id)
                                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
                productRepositry.delete(product);
        }

        @Transactional(isolation =Isolation.READ_COMMITTED)
        @Override
        public ProductResponseDto updateProduct(ProductRequestDto dto, long id) throws IOException {
                Product product = productRepositry.findById(id)
                                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

                Category category = categoryRepositry.findById(dto.getCategoryId())
                                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

                product.setName(dto.getName());
                product.setPrice(dto.getPrice());
                product.setDescription(dto.getDescription());
                product.setShort_description(dto.getShort_description());
                product.setRating(dto.getRating());
                product.setCategory(category);

                if (dto.getImage_url() != null && !dto.getImage_url().isEmpty()) {
                        String filename = uploadFile(dto.getImage_url());
                        product.setImage_url(filename);
                }

                Product updateProduct = productRepositry.save(product);
                return mapToResponseDto(updateProduct);
        }
}
