$("#region-table").DataTable({
  ajax: {
    url: "api/country",
    dataSrc: "",
  },
  columns: [
    {
      data: "null",
      render: (data, type, row, meta) => {
        return meta.row + 1;
      },
    },
    { data: "code" },
    { data: "name" },
    { data: "region.name" },
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
                  onclick="deleteCountry(${data.id})"
                  >
                  <i class="bi bi-trash fs-5"></i>
                  </button>
              </td>
          `;
      },
    },
  ],
});

// load data region
$.ajax({
  method: "GET",
  url: "api/region",
  dataType: "JSON",
  success: (res) => {
    const selectElement = $("#create-region");
    const text = "<option selected disabled>Select Region</option>";
    selectElement.empty();
    selectElement.append(text);
    res.forEach((region) => {
      const option = `<option value="${region.id}">${region.name}</option>`;
      selectElement.append(option);
    });

    const updateSelectElement = $("#update-region");
    updateSelectElement.empty();

    res.forEach((region) => {
      const option = `<option value="${region.id}">${region.name}</option>`;
      updateSelectElement.append(option);
    });
  },
});

function getUpdate(id) {
  $.ajax({
    method: "GET",
    url: "api/country/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      $("#update-id").val(res.id);
      $("#update-code").val(res.code);
      $("#update-name").val(res.name);
      $("#update-region").val(`${res.region.id}`);
    },
  });
}

createCountry = () => {
  let codeVal = $("#create-code").val();
  let nameVal = $("#create-name").val();
  let regionVal = $("#create-region").find(":selected").val();

  $.ajax({
    method: "POST",
    url: "api/country",
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    contentType: "application/json",
    data: JSON.stringify({
      code: codeVal,
      name: nameVal,
      region: {
        id: regionVal,
      },
    }),
    success: (res) => {
      $("#create").modal("hide");
      $("#region-table").DataTable().ajax.reload();
      $("#create-name").val("");
      $("#create-code").val("");
      Swal.fire({
        icon: "success",
        title: "Create Country",
        text: "Data country saved",
      });
    },
  });
};

getDetail = (id) => {
  $.ajax({
    method: "GET",
    url: "api/country/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      $("#detail-code").val(res.code);
      $("#detail-name").val(res.name);
      $("#detail-region").val(res.region.name);
    },
  });
};

updateCountry = () => {
  let id = $("#update-id").val();
  let codeVal = $("#update-code").val();
  let nameVal = $("#update-name").val();
  let regionVal = $("#update-region").find(":selected").val();
  $.ajax({
    method: "PUT",
    url: "api/country/" + id,
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    contentType: "application/json",
    data: JSON.stringify({
      code: codeVal,
      name: nameVal,
      region: {
        id: regionVal,
      },
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

deleteCountry = (id) => {
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
        url: "api/country/" + id,
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

