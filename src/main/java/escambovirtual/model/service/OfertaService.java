package escambovirtual.model.service;

import escambovirtual.model.ConnectionManager;
import escambovirtual.model.base.service.BaseOfertaService;
import escambovirtual.model.dao.ItemDAO;
import escambovirtual.model.dao.OfertaDAO;
import escambovirtual.model.entity.Item;
import escambovirtual.model.entity.Oferta;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joao
 */
public class OfertaService implements BaseOfertaService {

    @Override
    public void create(Oferta entity) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            OfertaDAO dao = new OfertaDAO();
            dao.create(conn, entity);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
            throw e;
        }
    }

    @Override
    public Oferta readById(Long id) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Oferta oferta = null;
        try {
            OfertaDAO dao = new OfertaDAO();
            oferta = dao.readById(conn, id);
            if(oferta != null){
                ItemDAO itemDAO = new ItemDAO();
                for(Item item : oferta.getOfertaItem().getItemList()){
                    item.setItemImagemList(itemDAO.readImagesHashByItemId(conn, item.getId()));
                }
            }
            conn.close();
        } catch (Exception e) {
            conn.close();
            throw e;
        }
        return oferta;
    }

    @Override
    public List<Oferta> readByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        List<Oferta> ofertaList = null;
        try {
            OfertaDAO dao = new OfertaDAO();
            ofertaList = dao.readByCriteria(conn, criteria, null, null);
            if(ofertaList != null){
                ItemDAO itemDAO = new ItemDAO();
                for (Oferta oferta : ofertaList) {
                    oferta.getItem().setItemImagemList(itemDAO.readImagesHashByItemId(conn, oferta.getItem().getId()));
                }
            }
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.close();
            throw e;
        }
        return ofertaList;
    }

    @Override
    public void update(Oferta entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> validate(Map<String, Object> fields) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long countByCriteria(Map<Long, Object> criteria, Long limit, Long offset) throws Exception {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Long count = null;
        try {
            OfertaDAO dao = new OfertaDAO();
            count = dao.countByCriteria(conn, criteria, limit, offset);
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
            conn.close();
            throw e;
        }
        return count;
    }

}
