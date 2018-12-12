package ru.naumen.sd40.log.parser.implementations.sdng.data;

import org.influxdb.dto.Point;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.IDataWriter;
import ru.naumen.sd40.log.parser.implementations.sdng.data.actiondone.ActionDoneData;
import ru.naumen.sd40.log.parser.implementations.sdng.data.error.ErrorData;

import static ru.naumen.sd40.log.parser.implementations.sdng.data.actiondone.ActionsDataType.*;
import static ru.naumen.sd40.log.parser.implementations.sdng.data.error.ResponseDataType.*;


@Component
public class SdngDataWriter implements IDataWriter<SdngData>
{
    @Override
    public void writeFields(Point.Builder builder, long time, SdngData sdngData, boolean printCsvData)
    {
        ActionDoneData actionDoneData = sdngData.getActionDoneData();
        actionDoneData.calculate();
        ErrorData errorData = sdngData.getErrorData();
        if (printCsvData)
        {
            System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n",
                    time,
                    actionDoneData.getCount(),
                    actionDoneData.getMin(),
                    actionDoneData.getMean(),
                    actionDoneData.getStddev(),
                    actionDoneData.getPercent50(),
                    actionDoneData.getPercent95(),
                    actionDoneData.getPercent99(),
                    actionDoneData.getPercent999(),
                    actionDoneData.getMax(),
                    errorData.getErrorCount()));
        }

        builder.addField(COUNT, actionDoneData.getCount())
                .addField("min", actionDoneData.getMin())
                .addField(MEAN, actionDoneData.getMean())
                .addField(STDDEV, actionDoneData.getStddev())
                .addField(PERCENTILE50, actionDoneData.getPercent50())
                .addField(PERCENTILE95, actionDoneData.getPercent95())
                .addField(PERCENTILE99, actionDoneData.getPercent99())
                .addField(PERCENTILE999, actionDoneData.getPercent999())
                .addField(MAX, actionDoneData.getMax())
                .addField(ERRORS, errorData.getErrorCount())
                .addField(ADD_ACTIONS, actionDoneData.getAddObjectActions())
                .addField(EDIT_ACTIONS, actionDoneData.getEditObjectsActions())
                .addField(LIST_ACTIONS, actionDoneData.geListActions())
                .addField(COMMENT_ACTIONS, actionDoneData.getCommentActions())
                .addField(GET_FORM_ACTIONS, actionDoneData.getFormActions())
                .addField(GET_DT_OBJECT_ACTIONS, actionDoneData.getDtObjectActions())
                .addField(SEARCH_ACTIONS, actionDoneData.getSearchActions())
                .addField(GET_CATALOG_ACTIONS, actionDoneData.getCatalogsActions());
    }
}
