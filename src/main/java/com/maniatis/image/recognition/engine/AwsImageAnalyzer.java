package com.maniatis.image.recognition.engine;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;

public class AwsImageAnalyzer {
    AmazonRekognition amazonRekognition;

    public AwsImageAnalyzer(AmazonRekognition _amazonRekognition) {
        amazonRekognition = _amazonRekognition;

    }

    public DetectLabelsResult analyze(File imageFile) throws IOException {
        try {
            byte[] bytes = Files.readAllBytes(imageFile.toPath());

            DetectLabelsRequest detectLabelsRequest = new DetectLabelsRequest()
                    .withImage(new Image()
                            .withBytes(ByteBuffer.wrap(bytes)));

            return this.amazonRekognition.detectLabels(detectLabelsRequest);
        }
        catch(java.io.IOException exception){
            exception.printStackTrace();
            throw exception;
        }
    }
}
