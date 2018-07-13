package ru.sbtqa.tag.pagefactory.support;

import ru.sbtqa.tag.datajack.TestDataObject;
import ru.sbtqa.tag.datajack.adaptors.ExcelDataObjectAdaptor;
import ru.sbtqa.tag.datajack.adaptors.JsonDataObjectAdaptor;
import ru.sbtqa.tag.datajack.adaptors.PropertiesDataObjectAdaptor;
import ru.sbtqa.tag.datajack.exceptions.DataException;
import ru.sbtqa.tag.qautils.properties.Props;

/**
 *
 * @author sbt-sidochenko-vv
 */
public class DataProvider {

    private static TestDataObject dataContainer;
    private static String configCollection;

    public static TestDataObject getInstance() throws DataException {
        if (dataContainer == null) {
            configCollection = Props.get("data.initial.collection", null);
            String dataType = Props.get("data.type", "stash");
            switch (dataType) {
                case "json":
                    dataContainer = new JsonDataObjectAdaptor(
                            Props.get("data.folder"),
                            Props.get("data.initial.collection"),
                            Props.get("data.extension", "json")
                    );
                    break;
                case "properties":
                    dataContainer = new PropertiesDataObjectAdaptor(
                            Props.get("data.folder"),
                            Props.get("data.initial.collection"),
                            Props.get("data.extension", "properties")
                    );
                    break;
                case "excel":
                    dataContainer = new ExcelDataObjectAdaptor(
                            Props.get("data.folder"),
                            Props.get("data.initial.collection")
                    );
                    break;
                default:
                    throw new DataException(String.format("Data adaptor %s isn't supported", dataType));
            }
        }
        return dataContainer;
    }

    public static void updateCollection(TestDataObject newObject) {
        dataContainer = newObject;
    }

    /**
     * @return the configCollection
     */
    public static String getConfigCollection() {
        return configCollection;
    }

}
