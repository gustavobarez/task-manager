package br.com.gustavobarez.resource;

import java.time.LocalDateTime;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.gustavobarez.entity.Task;
import br.com.gustavobarez.entity.UpdateTaskRequestDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
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
@Tag(name = "Tasks", description = "Operations related to task management")
public class TaskResource {

    @POST
    @Transactional
    @Operation(summary = "Create task", description = "Function responsible for creating a new task")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Task created successfully", content = @Content(schema = @Schema(implementation = Task.class))),
            @APIResponse(responseCode = "400", description = "Invalid data"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response createTask(@Valid Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.persist();
        return Response.ok(task).build();
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update task", description = "Function responsible for updating an existing task")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Task updated successfully", content = @Content(schema = @Schema(implementation = Task.class))),
            @APIResponse(responseCode = "400", description = "Invalid data"),
            @APIResponse(responseCode = "404", description = "Task not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response updateTask(
            @Parameter(description = "Task ID", required = true) @PathParam("id") Long id,
            UpdateTaskRequestDTO request) {

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
    @Operation(summary = "Delete task", description = "Function responsible for performing logical deletion of a task")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Task deleted successfully"),
            @APIResponse(responseCode = "404", description = "Task not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response deleteTask(
            @Parameter(description = "Task ID", required = true) @PathParam("id") Long id) {
        Task task = Task.findById(id);
        task.setDeletedAt(LocalDateTime.now());
        task.persist();
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Find task by ID", description = "Function responsible for finding a specific task by ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Task found successfully", content = @Content(schema = @Schema(implementation = Task.class))),
            @APIResponse(responseCode = "404", description = "Task not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response findTask(
            @Parameter(description = "Task ID", required = true) @PathParam("id") Long id) {
        Task task = Task.findById(id);
        return Response.ok(task).build();
    }

    @GET
    @Operation(summary = "List all tasks", description = "Function responsible for listing all available tasks")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Task list returned successfully", content = @Content(schema = @Schema(implementation = Task.class))),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response findAllTask() {
        List<Task> tasks = Task.listAll();
        return Response.ok(tasks).build();
    }
}