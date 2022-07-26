//package com.xhu.reggie.utils;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.sns.AmazonSNSClient;
//import com.amazonaws.services.sns.AmazonSNSClientBuilder;
//import com.amazonaws.services.sns.model.MessageAttributeValue;
//import com.amazonaws.services.sns.model.PublishRequest;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class SMSUtils {
//
//    @Value("${cloud.aws.credentials.access-key}")
//    private static String accessKey;
//
//    @Value("${cloud.aws.credentials.secret-key}")
//    private static String secretKey;
//
//    @Value("${cloud.aws.region.static}")
//    private static String region;
//
//    public static AmazonSNSClient amazonSNSClient() {
//        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//        return (AmazonSNSClient) AmazonSNSClientBuilder
//                .standard()
//                .withRegion(region)
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .build();
//    }
//
//    public static void sendMessage(String message, String phoneNumber) {
//        Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
//        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
//                .withStringValue("Transactional") //Sets the type to promotional.
//                .withDataType("String"));
//
//        PublishRequest publishRequest = new PublishRequest();
//        publishRequest.setMessage(message);
//        publishRequest.setPhoneNumber(phoneNumber);
//        publishRequest.setMessageAttributes(smsAttributes);
//
//        amazonSNSClient().publish(publishRequest);
//    }
//
//}
