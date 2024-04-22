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

    public OrderStatus() {
        this.status = OrderStatusType.NEW;
        this.timestamp = new Date();
    }

    // getter
    public OrderStatusType getStatus() {
        return this.status;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    // setter
    public void setStatus(OrderStatusType newStatus) {
        this.status = newStatus;
    }

    public void setTimestamp(Date newTimestamp) {
        this.timestamp = newTimestamp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        OrderStatus other = (OrderStatus) obj;
        if (status != other.status) return false;
        if (timestamp == null) {
            if (other.timestamp != null) return false;
        } else if (!timestamp.equals(other.timestamp)) return false;
        return true;
    }
}
