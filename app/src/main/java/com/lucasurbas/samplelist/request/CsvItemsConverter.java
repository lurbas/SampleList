package com.lucasurbas.samplelist.request;

import com.lucasurbas.samplelist.model.Item;
import com.lucasurbas.samplelist.model.Items;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Arrays;

import au.com.bytecode.opencsv.CSVReader;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by M30 on 2015-05-31.
 */
public class CsvItemsConverter implements Converter {
    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {

        //Build reader instance
        //Read data.csv
        //Default seperator is comma
        //Default quote character is double quote
        //Start reading from line number 2 (line numbers start from zero)

        Items items = new Items();

        CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(body.in()), ',', '"', 1);


            //Read CSV line by line and use the string array as you want
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine != null) {
                    //Verifying the read data here
                    Item item = new Item();
                    item.setTitle(nextLine[0]);
                    item.setDescription(nextLine[1]);
                    item.setImageUrl(nextLine[2]);

                    items.getList().add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }
}
