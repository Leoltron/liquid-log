package ru.naumen.sd40.log.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.naumen.sd40.log.parser.implementations.sdng.data.actiondone.ActionDoneData;
import ru.naumen.sd40.log.parser.implementations.sdng.data.actiondone.ActionDoneDataParser;

public class ActionDoneParserTests
{

    private ActionDoneDataParser parser;
    private ActionDoneData data;

    @Before
    public void setUp()
    {
        parser = new ActionDoneDataParser();
        data = new ActionDoneData();
    }

    @Test
    public void mustParseAddAction() {
        //when
        parser.parseLine("Done(10): AddObjectAction", data);

        //then
        Assert.assertEquals(1, data.getAddObjectActions());
    }

    @Test
    public void mustParseFormActions() {

        //when
        parser.parseLine("Done(10): GetFormAction", data);
        parser.parseLine("Done(1): GetAddFormContextDataAction", data);

        //then
        Assert.assertEquals(2, data.getFormActions());
    }

    @Test
    public void mustParseEditObject() {

        //when
        parser.parseLine("Done(10): EditObjectAction", data);

        //then
        Assert.assertEquals(1, data.getEditObjectsActions());
    }

    @Test
    public void mustParseSearchObject()
    {
        //when
        parser.parseLine("Done(10): GetPossibleAgreementsChildsSearchAction", data);
        parser.parseLine("Done(10): TreeSearchAction", data);
        parser.parseLine("Done(10): GetSearchResultAction", data);
        parser.parseLine("Done(10): GetSimpleSearchResultsAction", data);
        parser.parseLine("Done(10): SimpleSearchAction", data);
        parser.parseLine("Done(10): ExtendedSearchByStringAction", data);
        parser.parseLine("Done(10): ExtendedSearchByFilterAction", data);

        //then
        Assert.assertEquals(7, data.getSearchActions());
    }

    @Test
    public void mustParseGetList()
    {
        //when:
        parser.parseLine("Done(10): GetDtObjectListAction", data);
        parser.parseLine("Done(10): GetPossibleCaseListValueAction", data);
        parser.parseLine("Done(10): GetPossibleAgreementsTreeListActions", data);
        parser.parseLine("Done(10): GetCountForObjectListAction", data);
        parser.parseLine("Done(10): GetDataForObjectListAction", data);
        parser.parseLine("Done(10): GetPossibleAgreementsListActions", data);
        parser.parseLine("Done(10): GetDtObjectForRelObjListAction", data);

        //then:
        Assert.assertEquals(7, data.geListActions());
    }

    @Test
    public void mustParseComment()
    {
        //when:
        parser.parseLine("Done(10): EditCommentAction", data);
        parser.parseLine("Done(10): ChangeResponsibleWithAddCommentAction", data);
        parser.parseLine("Done(10): ShowMoreCommentAttrsAction", data);
        parser.parseLine("Done(10): CheckObjectsExceedsCommentsAmountAction", data);
        parser.parseLine("Done(10): GetAddCommentPermissionAction", data);
        parser.parseLine("Done(10): GetCommentDtObjectTemplateAction", data);

        //then:
        Assert.assertEquals(6, data.getCommentActions());
    }

    @Test
    public void mustParseDtObject()
    {
        //when:
        parser.parseLine("Done(10): GetVisibleDtObjectAction", data);
        parser.parseLine("Done(10): GetDtObjectsAction", data);
        parser.parseLine("Done(10): GetDtObjectTreeSelectionStateAction", data);
        parser.parseLine("Done(10): AbstractGetDtObjectTemplateAction", data);
        parser.parseLine("Done(10): GetDtObjectTemplateAction", data);

        //then:
        Assert.assertEquals(5, data.getDtObjectActions());
    }

    @Test
    public void mustParseGetCatalogs() {
        //when
        parser.parseLine("Done(10): GetCatalogsAction", data);

        //then
        Assert.assertEquals(1, data.getCatalogsActions());
    }

}
