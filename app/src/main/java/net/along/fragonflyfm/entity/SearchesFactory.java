package net.along.fragonflyfm.entity;

import net.along.fragonflyfm.Constants.DbConstants;
import net.along.fragonflyfm.util.AppUtils;
import net.lzzy.sqllib.SqlRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SearchesFactory {
    private static final SearchesFactory ourInstance = new SearchesFactory();
    private SqlRepository<Searches> repository;

    //单例示例
    public static SearchesFactory getInstance() {
        return ourInstance;
    }

    //连接一个常量
    private SearchesFactory() {
        repository = new SqlRepository<>(AppUtils.getContext(), Searches.class, DbConstants.packager);
    }

    //查找全部
    public List<Searches> getAll() {
        return repository.get();
    }

    //id查找
    public Searches getById(UUID id) {
        return repository.getById(id.toString());
    }

    //搜索
    public List<Searches> search(String kw) {
        List<Searches> searches = new ArrayList<>();
        List<Searches> all = repository.get();
        for (Searches c : all) {
            if (c.toString().contains(kw)) {
                searches.add(c);
            }
        }
        return searches;
    }

    //查询是否存在
    private boolean isCinemaExlsts(Searches cinema) throws InstantiationException, IllegalAccessException {
        List<Searches> localCinemas = repository.getByKeyword(cinema.getLocation(),
                new String[]{Searches.COL_LOCATION}, true);
        for (Searches c : localCinemas) {
            if (c.getName().equals(cinema.getName())) {
                return true;
            }
        }
        return false;
    }

    //add增加
    public boolean addCinema(Searches searches) {
        try {
            if (!isCinemaExlsts(searches)) {
                repository.insert(searches);
                return true;
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return false;
    }

    //确认是否删除
    private boolean canCinemaDeleted(Searches searches) {
        // TODO:2020/5/18 OrderFactory完成后再重写该方法，判断影院是否已有订单
//        return OrderFactory.getInstance().getOrdersByCinema(Searches.getId().toString()).size() == 0;
        return false;
    }

    //删除
    public boolean removeCinema(Searches searches) {
        if (canCinemaDeleted(searches)) {
            repository.delete(searches);
            return true;
        }
        return false;
    }
}
