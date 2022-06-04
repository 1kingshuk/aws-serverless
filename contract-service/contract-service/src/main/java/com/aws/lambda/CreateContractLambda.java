package com.aws.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.constant.ServiceConstants;
import com.aws.lambda.model.Contract;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Calendar;
import java.util.UUID;

public class CreateContractLambda {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());

    public APIGatewayProxyResponseEvent createContract(APIGatewayProxyRequestEvent request) throws JsonMappingException, JsonProcessingException {

        Contract contract = objectMapper.readValue(request.getBody(), Contract.class);
        contract.setSourceUniqueId(UUID.randomUUID().toString());
        contract.setCreatedDate(Calendar.getInstance().getTime().toString());

        Table table = dynamoDB.getTable(System.getenv(ServiceConstants.CONTRACT_DETAILS_TABLE));
        Item item = new Item().withPrimaryKey(ServiceConstants.CONTRACT_ID, contract.contractId)
                .withString(ServiceConstants.CONTRACT_TYPE, contract.contractType)
                .withBigInteger(ServiceConstants.ACCOUNT_NUMBER, contract.accountNumber)
                .withString(ServiceConstants.SOURCE_UNIQUE_ID, contract.sourceUniqueId)
                .withString(ServiceConstants.CREATED_DATE, contract.createdDate);
        table.putItem(item);

        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Contract created with contract Id: " + contract.contractId);
    }
}
