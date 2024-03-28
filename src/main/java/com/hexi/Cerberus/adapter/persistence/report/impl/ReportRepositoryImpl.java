package com.hexi.Cerberus.adapter.persistence.report.impl;

import com.hexi.Cerberus.domain.report.Report;
import com.hexi.Cerberus.domain.report.ReportID;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.infrastructure.query.Query;

import java.util.List;
import java.util.Optional;

public class ReportRepositoryImpl implements ReportRepository {
    @Override
    public Optional<Report> displayById(ReportID id) {
        return Optional.empty();
    }

    @Override
    public List<Report> displayById(List<ReportID> reportIDS) {
        return null;
    }

    @Override
    public List<Report> displayAll(Query query) {
        return null;
    }

    @Override
    public List<Report> displayAll() {
        return null;
    }

    @Override
    public Report append(Report user) {
        return null;
    }

    @Override
    public void update(Report user) {

    }

    @Override
    public void deleteById(ReportID id) {

    }

    @Override
    public boolean isExists(ReportID id) {
        return false;
    }
}
