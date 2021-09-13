<template>
  <div>
    <h2 id="page-heading" data-cy="VentiradHeading">
      <span v-text="$t('configmeApp.ventirad.home.title')" id="ventirad-heading">Ventirads</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.ventirad.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'VentiradCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-ventirad"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.ventirad.home.createLabel')"> Create a new Ventirad </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && ventirads && ventirads.length === 0">
      <span v-text="$t('configmeApp.ventirad.home.notFound')">No ventirads found</span>
    </div>
    <div class="table-responsive" v-if="ventirads && ventirads.length > 0">
      <table class="table table-striped" aria-describedby="ventirads">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ventirad.rangeFanSpeed')">Range Fan Speed</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ventirad.noise')">Noise</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ventirad.hasThermalPaste')">Has Thermal Paste</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ventirad.dimension') + ' (L, l, H)'">Dimension</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ventirad in ventirads" :key="ventirad.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VentiradView', params: { ventiradId: ventirad.id } }">{{ ventirad.id }}</router-link>
            </td>
            <td>{{ ventirad.rangeFanSpeed }}</td>
            <td>{{ ventirad.noise }}</td>
            <td>{{ ventirad.hasThermalPaste }}</td>
            <td>{{ ventirad.dimension.length }}mm, {{ ventirad.dimension.width }}mm, {{ ventirad.dimension.height }}mm</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'VentiradView', params: { ventiradId: ventirad.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'VentiradEdit', params: { ventiradId: ventirad.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(ventirad)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="configmeApp.ventirad.delete.question" data-cy="ventiradDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-ventirad-heading" v-text="$t('configmeApp.ventirad.delete.question', { id: removeId })">
          Are you sure you want to delete this Ventirad?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-ventirad"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeVentirad()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./ventirad.component.ts"></script>
