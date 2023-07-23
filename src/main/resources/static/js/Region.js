$("#region-table").DataTable({
  ajax: {
    url: "api/region",
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
                onclick="deleteRegion(${data.id})"
                >
                <i class="bi bi-trash fs-5"></i>
                </button>
            </td>
        `;
      },
    },
  ],
});

deleteRegion = (id) => {
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
        url: "api/region/" + id,
        dataType: "JSON",
        beforeSend: addCsrfToken(),
        contentType: "application/json",
        success: (res) => {
          $("#region-table").DataTable().ajax.reload();
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
    url: "api/region/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      $("#detail-name").val(res.name);
    },
  });
};

createRegion = () => {
  let nameVal = $("#create-name").val();

  $.ajax({
    method: "POST",
    url: "api/region",
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({
      name: nameVal,
    }),
    success: (res) => {
      $("#create").modal("hide");
      $("#region-table").DataTable().ajax.reload();
      $("#create-name").val("");
      Swal.fire({
        icon: "success",
        title: "Create Region",
        text: "Data region saved",
      });
    },
  });
};

updateRegion = () => {
  let nameVal = $("#update-name").val();
  let id = $("#update-id").val();

  $.ajax({
    method: "PUT",
    url: "api/region/" + id,
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    contentType: "application/json",
    data: JSON.stringify({
      name: nameVal,
    }),
    success: (res) => {
      $("#region-table").DataTable().ajax.reload();
      $("#update-name").val("");
      Swal.fire({
        icon: "success",
        title: "Update Region",
        text: "Data region updated",
      });
      $("#update").modal("hide");
    },
  });
};

function getUpdate(id) {
  $.ajax({
    method: "GET",
    url: "api/region/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      $("#update-id").val(res.id);
      $("#update-name").val(res.name);
    },
  });
}

