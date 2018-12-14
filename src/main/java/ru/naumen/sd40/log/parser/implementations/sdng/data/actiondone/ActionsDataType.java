package ru.naumen.sd40.log.parser.implementations.sdng.data.actiondone;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.statdata.AbstractDataType;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

@Component
public class ActionsDataType extends AbstractDataType
{
    public static final String ADD_ACTIONS = "addActions";
    public static final String EDIT_ACTIONS = "editActions";
    public static final String LIST_ACTIONS = "listActions";
    public static final String COMMENT_ACTIONS = "commentActions";
    public static final String GET_FORM_ACTIONS = "getFormActions";
    public static final String GET_DT_OBJECT_ACTIONS = "getDtObjectActions";
    public static final String SEARCH_ACTIONS = "searchActions";
    public static final String GET_CATALOG_ACTIONS = "getCatalogActions";
    public static final String ACTIONS_COUNT = "count";

    public static final String PATH_PART = "actions";

    private static List<String> getProps()
    {
        return Lists.newArrayList(TIME, ADD_ACTIONS, EDIT_ACTIONS, LIST_ACTIONS, COMMENT_ACTIONS, ACTIONS_COUNT,
                GET_FORM_ACTIONS, GET_DT_OBJECT_ACTIONS, SEARCH_ACTIONS, GET_CATALOG_ACTIONS);
    }

    public ActionsDataType()
    {
        super(getProps(), PATH_PART, "history_actions", "Performed actions");
    }
}
