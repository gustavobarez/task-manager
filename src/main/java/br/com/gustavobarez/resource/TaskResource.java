package br.com.gustavobarez.resource;

import java.time.LocalDateTime;

import br.com.gustavobarez.entity.Task;
import br.com.gustavobarez.entity.UpdateTaskRequestDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
    public Response createTask(@Valid Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.persist();
        return Response.ok(task).build();
    }

    @PATCH()
    @Path("/{id}")
    @Transactional
    public Response updateTask(@PathParam("id") Long id, UpdateTaskRequestDTO request) {
        if (request.title() == null && request.description() == null && request.status() == null) {
            throw new IllegalArgumentException("All fields cannot be empty");
        }
        Task task = Task.findById(id);
        if (request.title() != null) {
            task.setTitle(request.title());
        }
        if (request.description() != null) {
            task.setDescription(request.description());
        }
        if (request.status() != null) {
            task.setStatus(request.status());
        }
        task.setUpdatedAt(LocalDateTime.now());
        task.persist();
        return Response.ok(task).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteTask(@PathParam("id") Long id) {
        Task task = Task.findById(id);
        task.setDeletedAt(LocalDateTime.now());
        task.persist();
        return Response.ok().build();
    }

}
