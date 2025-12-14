package com.brasilburger.service.impl;

import com.brasilburger.config.CloudinaryConfig;
import com.brasilburger.service.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.util.Map;

public class ImageServiceImpl implements ImageService {

    private Cloudinary cloudinary;

    public ImageServiceImpl() {
        this.cloudinary = CloudinaryConfig.getCloudinary();
    }

    @Override
    public String uploadImage(String cheminFichier, String dossier) {
        try {
            File fichier = new File(cheminFichier);

            if (!fichier.exists()) {
                System.err.println("❌ Le fichier n'existe pas: " + cheminFichier);
                return null;
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(
                fichier,
                ObjectUtils.asMap(
                    "folder", "brasil-burger/" + dossier,
                    "resource_type", "image"
                )
            );

            String imageUrl = (String) uploadResult.get("secure_url");
            return imageUrl;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void supprimerImage(String imageUrl) {
        try {
            if (imageUrl == null || imageUrl.isEmpty()) {
                return;
            }

            String publicId = extrairePublicId(imageUrl);

            if (publicId != null) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extrairePublicId(String imageUrl) {
        try {
            String[] parts = imageUrl.split("/upload/");
            if (parts.length < 2) {
                return null;
            }

            String afterUpload = parts[1];
            String[] versionParts = afterUpload.split("/", 2);
            if (versionParts.length < 2) {
                return null;
            }

            String pathWithExtension = versionParts[1];
            int lastDot = pathWithExtension.lastIndexOf('.');
            if (lastDot > 0) {
                return pathWithExtension.substring(0, lastDot);
            }

            return pathWithExtension;
        } catch (Exception e) {
            return null;
        }
    }
}
