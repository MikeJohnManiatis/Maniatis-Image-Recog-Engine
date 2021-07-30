package com.maniatis.image.recognition.engine.rest;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.maniatis.image.recognition.engine.AwsImageAnalyzer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestAwsImageAnalyzer {
    AwsImageAnalyzer awsImageAnalyzer;

    @Before
    public void before(){
        BasicAWSCredentials awsCredentials =
                new BasicAWSCredentials("accessKEY", "secretKey");

        awsImageAnalyzer = new AwsImageAnalyzer(AmazonRekognitionClientBuilder
                        .standard()
                        .withRegion(Regions.US_EAST_1)
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                        .build()
        );

    }
    @Test
    public void happyPath(){
        Path path = Paths.get("src", "test", "resources", "images", "hotdog.jpeg");
        File file = new File(path.toString());

        try {
            DetectLabelsResult detectLabelsResult =
                    awsImageAnalyzer.analyze(file);

            Assert.assertEquals("hot dog", detectLabelsResult.getLabels().stream()
                    .findFirst()
                    .get()
                    .getName()
                    .toLowerCase());

        } catch(Exception ex){
            ex.printStackTrace();
            Assert.fail();
        }

    }
}
