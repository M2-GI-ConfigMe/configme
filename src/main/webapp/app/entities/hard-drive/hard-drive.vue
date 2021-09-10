<template>
  <div>
    <h2 id="page-heading" data-cy="HardDriveHeading">
      <span v-text="$t('configmeApp.hardDrive.home.title')" id="hard-drive-heading">Hard Drives</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.hardDrive.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'HardDriveCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-hard-drive"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.hardDrive.home.createLabel')"> Create a new Hard Drive </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && hardDrives && hardDrives.length === 0">
      <span v-text="$t('configmeApp.hardDrive.home.notFound')">No hardDrives found</span>
    </div>
    <div class="table-responsive" v-if="hardDrives && hardDrives.length > 0">
      <table class="table table-striped" aria-describedby="hardDrives">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('configmeApp.hardDrive.capacity')">Capacity</span></th>
            <th scope="row"><span v-text="$t('configmeApp.hardDrive.speedWrite')">Speed Write</span></th>
            <th scope="row"><span v-text="$t('configmeApp.hardDrive.speedRead')">Speed Read</span></th>
            <th scope="row"><span v-text="$t('configmeApp.hardDrive.type')">Type</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="hardDrive in hardDrives" :key="hardDrive.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'HardDriveView', params: { hardDriveId: hardDrive.id } }">{{ hardDrive.id }}</router-link>
            </td>
            <td>{{ hardDrive.capacity }}</td>
            <td>{{ hardDrive.speedWrite }}</td>
            <td>{{ hardDrive.speedRead }}</td>
            <td v-text="$t('configmeApp.MemoryType.' + hardDrive.type)">{{ hardDrive.type }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'HardDriveView', params: { hardDriveId: hardDrive.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'HardDriveEdit', params: { hardDriveId: hardDrive.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(hardDrive)"
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
        ><span id="configmeApp.hardDrive.delete.question" data-cy="hardDriveDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-hardDrive-heading" v-text="$t('configmeApp.hardDrive.delete.question', { id: removeId })">
          Are you sure you want to delete this Hard Drive?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-hardDrive"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeHardDrive()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./hard-drive.component.ts"></script>
