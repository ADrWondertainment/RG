package ruangong.root.service_xiao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ruangong.root.bean.Cuser;

@Service
@Repository
public interface CuserService {

    public Cuser getCuserByTypeID(int id);


}
