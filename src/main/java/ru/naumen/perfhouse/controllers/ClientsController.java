package ru.naumen.perfhouse.controllers;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.LogParseModes;
import ru.naumen.sd40.log.parser.UniversalLogParser;
import ru.naumen.sd40.log.parser.util.BufferedReaderBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dkirpichenkov on 26.10.16.
 */
@Controller
public class ClientsController
{
    private final Logger LOG = LoggerFactory.getLogger(ClientsController.class);
    private final InfluxDAO influxDAO;
    private final UniversalLogParser logParser;
    private final LogParseModes logParseModes;

    @Inject
    public ClientsController(InfluxDAO influxDAO, UniversalLogParser logParser, LogParseModes logParseModes)
    {
        this.influxDAO = influxDAO;
        this.logParser = logParser;
        this.logParseModes = logParseModes;
    }

    @RequestMapping(path = "/")
    public ModelAndView index()
    {
        List<String> clients = influxDAO.getDbList();
        HashMap<String, Object> clientLast864Links = new HashMap<>();
        HashMap<String, Object> clientLinks = new HashMap<>();
        HashMap<String, Object> clientMonthLinks = new HashMap<>();
        HashMap<String, Object> clientLast2016Links = new HashMap<>();
        HashMap<String, Object> clientPreviousMonthLinks = new HashMap<>();
        List<String> modes = logParseModes.getWorkingParseModes();

        DateTime now = DateTime.now();
        DateTime prevMonth = now.minusMonths(1);
        DateTime yesterday = now.minusDays(1);

        clients.forEach(it ->
        {
            clientLinks.put(it, "/history/" + it + "/" + yesterday.getYear() + "/" + yesterday.getMonthOfYear() + "/"
                    + yesterday.getDayOfMonth());

            clientMonthLinks.put(it, "/history/" + it + "/" + now.getYear() + "/" + now.getMonthOfYear());
            clientPreviousMonthLinks.put(it,
                    "/history/" + it + "/" + prevMonth.getYear() + "/" + prevMonth.getMonthOfYear());
            clientLast864Links.put(it, "/history/" + it + "?count=864");
            clientLast2016Links.put(it, "/history/" + it + "?count=2016");
        });

        HashMap<String, Object> model = new HashMap<>();
        model.put("clients", clients);
        model.put("parseModes", modes);
        model.put("links", clientLinks);
        model.put("monthlinks", clientMonthLinks);
        model.put("last864links", clientLast864Links);
        model.put("last2016links", clientLast2016Links);
        model.put("prevMonthLinks", clientPreviousMonthLinks);

        return new ModelAndView("clients", model, HttpStatus.OK);
    }

    @RequestMapping(path = "{client}", method = RequestMethod.POST)
    public void postClientStatFormat1(@PathVariable("client") String client, HttpServletRequest request,
                                      HttpServletResponse response) throws IOException
    {
        try
        {
            client = client.replaceAll("-", "_");
            influxDAO.connectToDB(client);
            String data = IOUtils.toString(request.getInputStream(), "UTF-8");
            JSONObject measure = new JSONObject(data);
            influxDAO.storeFromJSon(null, client, measure);
            response.sendError(HttpServletResponse.SC_OK);
        } catch (Exception ex)
        {
            LOG.error(ex.toString(), ex);
            throw ex;
        }
    }

    @RequestMapping(path = "/uploadLog", method = RequestMethod.POST)
    public void uploadLog(@RequestParam("file") MultipartFile file,
                          @RequestParam("dbName") String dbName,
                          @RequestParam("type") String type,
                          @RequestParam("timeZone") String timeZone,
                          @RequestParam(value = "printTraceResult", required = false) String printTraceResult
    )
    {
        boolean printOutput = printTraceResult != null;

        try
        {
            BufferedReaderBuilder builder = new BufferedReaderBuilder(new InputStreamReader(file.getInputStream()));
            logParser.parseAndUploadLogs(type, file.getOriginalFilename(), builder, dbName, timeZone, printOutput);
        } catch (Exception e)
        {
            LOG.error("An error occurred during input log parse:", e);
        }
    }
}
