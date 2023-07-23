$("#role-table").DataTable({
  ajax: {
    url: "api/role",
    dataSrc: "",
  },
  columns: [
    {
      data: "null",
      render: (data, type, row, meta) => {
        return meta.row + 1;
      },
    },
    { data: "name" },
    {
      data: null,
      render: function (data, type, row, meta) {
        return `
            <td class="d-flex justify-content-center align-items-center gap-3">
                <button
                type="button"
                class="btn btn-primary"
                data-bs-toggle="modal"
                data-bs-target="#detail"
                onclick="getDetail(${data.id})"
                >
                <i class="bi bi-box-arrow-in-up-right fs-5"></i>
                </button>
                <button
                type="button"
                class="btn btn-warning"
                data-bs-toggle="modal"
                data-bs-target="#update"
                onclick="getUpdate(${data.id})"
                >
                <i class="bi bi-file-text fs-5"></i>
                </button>
                <button
                type="button"
                class="btn btn-danger"
                onclick="deleteRole(${data.id})"
                >
                <i class="bi bi-trash fs-5"></i>
                </button>
            </td>
        `;
      },
    },
  ],
});

deleteRole = (id) => {
  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, delete it!",
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        method: "DELETE",
        url: "api/role/" + id,
        dataType: "JSON",
        beforeSend: addCsrfToken(),
        contentType: "application/json",
        success: (res) => {
          $("#role-table").DataTable().ajax.reload();
          Swal.fire({
            icon: "success",
            title: "Deleted!",
            text: `${res.name} has been deleted`,
          });
        },
      });
    }
  });
};

getDetail = (id) => {
  $.ajax({
    method: "GET",
    url: "api/role/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      $("#detail-name").val(res.name);
    },
  });
};

createRole = () => {
  let nameVal = $("#create-name").val();

  $.ajax({
    method: "POST",
    url: "api/role",
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({
      name: nameVal,
    }),
    success: (res) => {
      $("#create").modal("hide");
      $("#role-table").DataTable().ajax.reload();
      $("#create-name").val("");
      Swal.fire({
        icon: "success",
        title: "Create role",
        text: "Data role saved",
      });
    },
  });
};

updateRole = () => {
  let nameVal = $("#update-name").val();
  let id = $("#update-id").val();

  $.ajax({
    method: "PUT",
    url: "api/role/" + id,
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    contentType: "application/json",
    data: JSON.stringify({
      name: nameVal,
    }),
    success: (res) => {
      $("#role-table").DataTable().ajax.reload();
      $("#update-name").val("");
      Swal.fire({
        icon: "success",
        title: "Update role",
        text: "Data role updated",
      });
      $("#update").modal("hide");
    },
  });
};

function getUpdate(id) {
  $.ajax({
    method: "GET",
    url: "api/role/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      $("#update-id").val(res.id);
      $("#update-name").val(res.name);
    },
  });
}
