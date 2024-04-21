/*
 * NTU SC2002 project
 * Order Status
 */

package sg.edu.ntu.sc2002;

import java.io.Serializable;
import java.util.Date;

public class OrderStatus implements Serializable {

    private OrderStatusType status;
    private Date timestamp;

    public OrderStatus(){
        this.status = OrderStatusType.NEW;
        this.timestamp = new Date();
    }

    //getter
    public OrderStatusType getStatus(){
        return this.status;
    }

    public Date getTimestamp(){
        return this.timestamp;
    }

    //setter
    public void setStatus(OrderStatusType newStatus){
        this.status = newStatus;
    }

    public void setTimestamp(Date newTimestamp){
        this.timestamp = newTimestamp;
    }
}
