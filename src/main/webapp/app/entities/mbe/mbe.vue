<template>
  <div>
    <h2 id="page-heading" data-cy="MbeHeading">
      <span v-text="$t('configmeApp.mbe.home.title')" id="mbe-heading">Mbes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.mbe.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MbeCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-mbe">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.mbe.home.createLabel')"> Create a new Mbe </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && mbes && mbes.length === 0">
      <span v-text="$t('configmeApp.mbe.home.notFound')">No mbes found</span>
    </div>
    <div class="table-responsive" v-if="mbes && mbes.length > 0">
      <table class="table table-striped" aria-describedby="mbes">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('configmeApp.mbe.socketCpu')">Socket Cpu</span></th>
            <th scope="row"><span v-text="$t('configmeApp.mbe.ramType')">Ram Type</span></th>
            <th scope="row"><span v-text="$t('configmeApp.mbe.ramFrequencyMax')">Ram Frequency Max</span></th>
            <th scope="row"><span v-text="$t('configmeApp.mbe.ramSizeMax')">Ram Size Max</span></th>
            <th scope="row"><span v-text="$t('configmeApp.mbe.pciOutputs')">Pci Outputs</span></th>
            <th scope="row"><span v-text="$t('configmeApp.mbe.displayOutput')">Display Output</span></th>
            <th scope="row"><span v-text="$t('configmeApp.mbe.storageOutput')">Storage Output</span></th>
            <th scope="row"><span v-text="$t('configmeApp.mbe.insideIO')">Inside IO</span></th>
            <th scope="row"><span v-text="$t('configmeApp.mbe.backPanelOutput')">Back Panel Output</span></th>
            <th scope="row"><span v-text="$t('configmeApp.mbe.bios')">Bios</span></th>
            <th scope="row"><span v-text="$t('configmeApp.mbe.format')">Format</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="mbe in mbes" :key="mbe.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MbeView', params: { mbeId: mbe.id } }">{{ mbe.id }}</router-link>
            </td>
            <td v-text="$t('configmeApp.SocketType.' + mbe.socketCpu)">{{ mbe.socketCpu }}</td>
            <td v-text="$t('configmeApp.RamType.' + mbe.ramType)">{{ mbe.ramType }}</td>
            <td>{{ mbe.ramFrequencyMax }}</td>
            <td>{{ mbe.ramSizeMax }}</td>
            <td>{{ mbe.pciOutputs }}</td>
            <td>{{ mbe.displayOutput }}</td>
            <td>{{ mbe.storageOutput }}</td>
            <td>{{ mbe.insideIO }}</td>
            <td>{{ mbe.backPanelOutput }}</td>
            <td>{{ mbe.bios }}</td>
            <td v-text="$t('configmeApp.FormatType.' + mbe.format)">{{ mbe.format }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MbeView', params: { mbeId: mbe.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MbeEdit', params: { mbeId: mbe.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(mbe)"
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
        ><span id="configmeApp.mbe.delete.question" data-cy="mbeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-mbe-heading" v-text="$t('configmeApp.mbe.delete.question', { id: removeId })">
          Are you sure you want to delete this Mbe?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-mbe"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMbe()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./mbe.component.ts"></script>
