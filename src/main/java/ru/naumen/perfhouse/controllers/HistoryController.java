package ru.naumen.perfhouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.naumen.perfhouse.statdata.IDataType;
import ru.naumen.perfhouse.statdata.StatData;
import ru.naumen.perfhouse.statdata.StatDataService;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by doki on 23.10.16.
 */
@Controller
public class HistoryController
{
    private static final String NO_HISTORY_VIEW = "no_history";

    private final StatDataService service;
    private final HashMap<String, IDataType> pathToDataType = new HashMap<>();

    @Autowired
    public HistoryController(StatDataService service, List<IDataType> dataTypeList)
    {
        this.service = service;
        for (IDataType dataType : dataTypeList)
        {
            final String pathPartName = dataType.getPathPartName();
            IDataType previousValue = pathToDataType.put(pathPartName, dataType);
            if (previousValue != null)
            {
                throw new IllegalArgumentException(
                        String.format("Found a duplicate path \"%s\" associated with both %s and %s",
                                pathPartName,
                                previousValue.getClass().getName(),
                                dataType.getClass().getName()));
            }
        }
    }

    @RequestMapping(path = {"/history/{client}/{type:\\D.*}/{year:[\\d]+}/{month:[\\d]+}/{day:[\\d]+}",
            "/history/{client}/{year:[\\d]+}/{month:[\\d]+}/{day:[\\d]+}"})
    public ModelAndView indexByDay(@PathVariable("client") String client,
                                   @PathVariable(name = "type", required = false) String type,
                                   @PathVariable(name = "year", required = false) int year,
                                   @PathVariable(name = "month", required = false) int month,
                                   @PathVariable(name = "day", required = false) int day) throws ParseException
    {
        return getDataAndViewByDate(client, pathToDataType.get(type != null ? type : ""), year, month, day);
    }

    @RequestMapping(path = {"/history/{client}/{year:[\\d]+}/{month:[\\d]+}",
            "/history/{type:\\D.*}/{client}/{year:[\\d]+}/{month:[\\d]+}"})
    public ModelAndView indexByMonth(@PathVariable("client") String client,
                                     @PathVariable(name = "type", required = false) String type,
                                     @PathVariable(name = "year", required = false) int year,
                                     @PathVariable(name = "month", required = false) int month) throws ParseException
    {
        return getDataAndViewByDate(client, pathToDataType.get(type != null ? type : ""), year, month, 0, true);
    }

    @RequestMapping(path = {"/history/{client}", "/history/{client}/{type:\\D.*}"})
    public ModelAndView indexLast864(@PathVariable("client") String client,
                                     @PathVariable(name = "type", required = false) String type,
                                     @RequestParam(name = "count", defaultValue = "864") int count) throws ParseException
    {
        return getDataAndView(client, pathToDataType.get(type != null ? type : ""), count);
    }

    private ModelAndView getDataAndView(String client, IDataType dataType, int count)
            throws ParseException
    {
        ru.naumen.perfhouse.statdata.StatData data = service.getData(client, dataType, count);
        if (data == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }
        Map<String, Object> model = new HashMap<>(data.asModel());
        model.put("client", client);
        model.put("dataTypes", pathToDataType.values());

        return new ModelAndView(dataType.getViewName(), model, HttpStatus.OK);
    }

    private ModelAndView getDataAndViewByDate(String client, IDataType type, int year, int month, int day) throws ParseException
    {
        return getDataAndViewByDate(client, type, year, month, day, false);
    }

    private ModelAndView getDataAndViewByDate(String client, IDataType type, int year, int month, int day, boolean compress) throws ParseException
    {
        ru.naumen.perfhouse.statdata.StatData dataDate = service.getDataDate(client, type, year, month, day);
        if (dataDate == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }

        dataDate = compress ? service.compress(dataDate, 3 * 60 * 24 / 5) : dataDate;
        Map<String, Object> model = new HashMap<>(dataDate.asModel());
        model.put("client", client);
        model.put("year", year);
        model.put("month", month);
        model.put("day", day);
        model.put("dataTypes", pathToDataType.values());
        return new ModelAndView(type.getViewName(), model, HttpStatus.OK);
    }

    private ModelAndView getDataAndViewCustom(String client, IDataType dataType, String from, String to, int maxResults) throws ParseException
    {
        StatData data = service.getDataCustom(client, dataType, from, to);
        if (data == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }
        data = service.compress(data, maxResults);
        Map<String, Object> model = new HashMap<>(data.asModel());
        model.put("client", client);
        model.put("custom", true);
        model.put("from", from);
        model.put("to", to);
        model.put("maxResults", maxResults);
        model.put("dataTypes", pathToDataType.values());
        return new ModelAndView(dataType.getViewName(), model, HttpStatus.OK);
    }

    @RequestMapping(path = {"/history/{client}/custom/{type:\\D.*}", "/history/{client}/custom"})
    public ModelAndView customIndex(@PathVariable("client") String client,
                                    @PathVariable(name = "type", required = false) String type,
                                    @RequestParam("from") String from,
                                    @RequestParam("to") String to,
                                    @RequestParam("maxResults") int maxResults) throws ParseException
    {
        return getDataAndViewCustom(client, pathToDataType.get(type != null ? type : ""), from, to, maxResults);
    }
}
