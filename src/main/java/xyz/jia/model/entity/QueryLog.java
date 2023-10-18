package xyz.jia.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "query_logs")
@Data
public class QueryLog {
    /*
    * This database table can be used to record rest-api request details for analysis at a later stage.
    * Todo: Currently, we are recording basic information for demonstration purpose, this can be extended as per needs.
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_uri")
    private String requestUri;

    // todo: fix: recording one parameter in one column would be a good practice to search efficiently as a later stage
    @Column(name = "request_params")
    private String requestParams;

    @Column(name = "request_timestamp")
    private LocalDateTime requestTimestamp;
}
