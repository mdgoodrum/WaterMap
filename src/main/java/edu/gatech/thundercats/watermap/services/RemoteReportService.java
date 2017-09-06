package edu.gatech.thundercats.watermap.services;

import edu.gatech.thundercats.watermap.domain.PurityReport;
import edu.gatech.thundercats.watermap.domain.Report;
import edu.gatech.thundercats.watermap.domain.SourceReport;
import edu.gatech.thundercats.watermap.network.APIServerConnection;
import javafx.concurrent.Task;
import org.json.JSONObject;

/**
 * Created by Matthieu Gay-Bellile on 11/1/16.
 */
public class RemoteReportService implements ReportService {
    /**
     * API Server Connection needed for remote persistence.
     */
    private final APIServerConnection api;

    /**
     * Sets the api server connection in the class constuctor
     *
     * @param api api server connection object
     */
    public RemoteReportService(final APIServerConnection api) {
        this.api = api;
    }

    @Override
    public final Task<SourceReport[]> getSourceReports() {
        return new Task<SourceReport[]>() {
            @Override
            protected SourceReport[] call() throws Exception {
                return api.getAsObject("/source_reports", SourceReport[].class,
                        error -> updateMessage(error));
            }
        };
    }

    @Override
    public final Task<PurityReport[]> getPurityReports() {
        return new Task<PurityReport[]>() {
            @Override
            protected PurityReport[] call() throws Exception {
                return api.getAsObject("/purity_reports", PurityReport[].class,
                        error -> updateMessage(error));
            }
        };
    }

    @Override
    public final <T extends Report> Task<Boolean> addReport(final T report) {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                JSONObject response;
                if (report instanceof SourceReport) {
                    response = api.post("/source_reports", report,
                            error -> updateMessage(error));
                } else if (report instanceof PurityReport) {
                    response = api.post("/purity_reports", report,
                            error -> updateMessage(error));
                } else {
                    throw new IllegalArgumentException("Report must be an " +
                            "instance of either SourceReport or PurityReport");
                }

                updateMessage(response.getString("message"));
                return response.getBoolean("success");
            }
        };
    }
}
