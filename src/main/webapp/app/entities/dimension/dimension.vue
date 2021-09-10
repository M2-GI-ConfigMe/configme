<template>
  <div>
    <h2 id="page-heading" data-cy="DimensionHeading">
      <span v-text="$t('configmeApp.dimension.home.title')" id="dimension-heading">Dimensions</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.dimension.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DimensionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-dimension"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.dimension.home.createLabel')"> Create a new Dimension </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && dimensions && dimensions.length === 0">
      <span v-text="$t('configmeApp.dimension.home.notFound')">No dimensions found</span>
    </div>
    <div class="table-responsive" v-if="dimensions && dimensions.length > 0">
      <table class="table table-striped" aria-describedby="dimensions">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('configmeApp.dimension.height')">Height</span></th>
            <th scope="row"><span v-text="$t('configmeApp.dimension.width')">Width</span></th>
            <th scope="row"><span v-text="$t('configmeApp.dimension.length')">Length</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="dimension in dimensions" :key="dimension.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DimensionView', params: { dimensionId: dimension.id } }">{{ dimension.id }}</router-link>
            </td>
            <td>{{ dimension.height }}</td>
            <td>{{ dimension.width }}</td>
            <td>{{ dimension.length }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DimensionView', params: { dimensionId: dimension.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DimensionEdit', params: { dimensionId: dimension.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(dimension)"
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
        ><span id="configmeApp.dimension.delete.question" data-cy="dimensionDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-dimension-heading" v-text="$t('configmeApp.dimension.delete.question', { id: removeId })">
          Are you sure you want to delete this Dimension?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-dimension"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDimension()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./dimension.component.ts"></script>
