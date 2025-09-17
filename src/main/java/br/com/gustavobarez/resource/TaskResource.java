package br.com.gustavobarez.resource;

import java.time.LocalDateTime;

import br.com.gustavobarez.entity.Task;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TaskResource {

    @POST
    @Transactional
    public Response createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.persist();
        return Response.ok(task).build();
    }

}
