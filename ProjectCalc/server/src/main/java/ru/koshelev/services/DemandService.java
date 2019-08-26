package ru.koshelev.services;

import ru.koshelev.commons.entities.Demand;
import ru.koshelev.dao.DemandDao;

public class DemandService {
    private DemandDao demandDao = new DemandDao();

    public DemandService() {
    }

    public Demand findDemand(int id) {
        return demandDao.findById(id);
    }

    public void saveDemand(Demand demand) {
        System.out.println("saveProduct");
        demandDao.save(demand);
    }

}
