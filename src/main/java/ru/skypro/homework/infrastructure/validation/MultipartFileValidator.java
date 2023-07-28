package ru.skypro.homework.infrastructure.validation;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class MultipartFileValidator implements ConstraintValidator<MultipartFileConstraint, MultipartFile> {
    private Set<String> contentTypes;
    private long maxSize;
    private long minSize;

    @Override
    public void initialize(MultipartFileConstraint constraintAnnotation) {
        contentTypes = Set.of(constraintAnnotation.contentTypes());
        maxSize = constraintAnnotation.maxSize();
        minSize = constraintAnnotation.minSize();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (!contentTypes.contains(value.getContentType())) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(buildMessageForContentType(contentTypes, value.getContentType()))
                    .addConstraintViolation();
            return false;
        }
        long size = value.getSize();
        if (!(minSize <= size && size <= maxSize)) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(buildMessageForSize(minSize, maxSize, value.getSize()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private String buildMessageForSize(long minSize, long maxSize, long fileSize) {
        return String.format("File's size[%d] must be in [%d,%d]", fileSize, minSize, maxSize);
    }


    private String buildMessageForContentType(Set<String> contentTypes, String fileContentType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Accept content types")
                .append("[");
        int k = 1;
        for (String contentType : contentTypes) {
            stringBuilder.append(contentType);
            if (k != contentTypes.size()) {
                stringBuilder.append(", ");
            }
            k++;
        }
        stringBuilder.append("]")
                .append("not contains file content type[").append(fileContentType).append("].");
        return stringBuilder.toString();
    }

}
