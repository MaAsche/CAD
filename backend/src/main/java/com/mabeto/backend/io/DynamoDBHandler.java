package com.mabeto.backend.io;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.mabeto.backend.picture.model.PictureInformation;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class DynamoDBHandler {
    public static final String TABLE_NAME = "pictures";

    private final AmazonDynamoDB client;
    private final Table table;

    public DynamoDBHandler() {
        client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new PropertiesFileCredentialsProvider("config/credentials"))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable(TABLE_NAME);
    }

    public void putInformation(PictureInformation information) {
        try {
            Item item = new Item().withPrimaryKey("id", information.id)
                    .withString("description", information.description)
                    .withLong("created", information.createdAt);
            PutItemOutcome outcome = table.putItem(item);
            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
        } catch (Exception e) {
            System.err.println("Unable to add item: " + information.id);
            System.err.println(e.getMessage());
        }
    }

    public PictureInformation getInformation(String id) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("id", id);
        Item item = table.getItem(spec);
        String description = item.getString("description");
        long created = item.getLong("created");
        System.out.println("GetItem succeeded: " + id);
        return new PictureInformation(id, description, created);
    }

    public List<PictureInformation> getAllInformation() {
        List<PictureInformation> allInformation = new LinkedList<>();
        ScanRequest request = new ScanRequest().withTableName(TABLE_NAME);
        ScanResult result = client.scan(request);
        for (Map<String, AttributeValue> item : result.getItems()){
            if (!item.containsKey("id"))
                continue;
            String id =  item.get("id").getS();
            String description = item.get("description").getS();
            long created = Long.parseLong(item.get("created").getN());
            PictureInformation information = new PictureInformation(id, description, created);
            allInformation.add(information);
        }
        return List.copyOf(allInformation);
    }

    public void deleteInformation(String id) {
        table.deleteItem("id", id);
    }
}
