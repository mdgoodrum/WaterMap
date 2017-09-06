package edu.gatech.thundercats.watermap.services;

import edu.gatech.thundercats.watermap.domain.PurityReport;
import edu.gatech.thundercats.watermap.domain.Report;
import edu.gatech.thundercats.watermap.domain.SourceReport;
import javafx.concurrent.Task;

/**
 * Created by lavorgia on 10/11/16.
 */
public interface ReportService {
    /**
     * @return Source reports
     */
    Task<SourceReport[]> getSourceReports();

    /**
     * @return Purity reports
     */
    Task<PurityReport[]> getPurityReports();

    /**
     * @param report report to be added
     * @param <T>    Made Generic
     * @return If adding report was successful
     */
    <T extends Report> Task<Boolean> addReport(T report);
}
