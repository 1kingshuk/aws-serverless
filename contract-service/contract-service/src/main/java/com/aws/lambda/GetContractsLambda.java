package com.aws.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.constant.ServiceConstants;
import com.aws.lambda.model.Contract;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class GetContractsLambda {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();

    public APIGatewayProxyResponseEvent getContracts(APIGatewayProxyRequestEvent request) throws JsonMappingException, JsonProcessingException {

        ScanResult scanResult = dynamoDB.scan(new ScanRequest().withTableName(System.getenv(ServiceConstants.CONTRACT_DETAILS_TABLE)));
        List<Contract> contracts = scanResult.getItems().stream()
                .map(item -> new Contract(new BigInteger(item.get(ServiceConstants.CONTRACT_ID).getN()),
                                item.get(ServiceConstants.CONTRACT_TYPE).getS(),
                                new BigInteger(item.get(ServiceConstants.ACCOUNT_NUMBER).getN()),
                                item.get(ServiceConstants.SOURCE_UNIQUE_ID).getS(),
                                item.get(ServiceConstants.CREATED_DATE).getS())
                ).collect(Collectors.toList());
        String output = objectMapper.writeValueAsString(contracts);

        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(output);
    }
}
