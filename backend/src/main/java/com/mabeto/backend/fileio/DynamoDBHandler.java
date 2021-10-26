package com.mabeto.backend.fileio;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.util.HashMap;
import java.util.Map;


public class DynamoDBHandler {
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.EU_WEST_2)
            .build();

    DynamoDB dynamoDB = new DynamoDB(client);
    Table table = dynamoDB.getTable("pictures");

    public void createPictureDetails(String filename){

        final String url = filename; // to be created

        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("url", filename);
        infoMap.put("someInfo", "someOtherInfo");

        try {
            System.out.println("Adding a new item...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("filename", filename).withMap("details", infoMap));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + filename );
            System.err.println(e.getMessage());
        }
    }

    public void loadPictureDetails(String filename) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("filename", filename);

        try {
            System.out.println("Attempting to read the item...");
            Item outcome = table.getItem(spec);
            System.out.println("GetItem succeeded: " + outcome);
        } catch (Exception e) {
            System.err.println("Unable to read item: " + filename);
            System.err.println(e.getMessage());
        }
    }
}
