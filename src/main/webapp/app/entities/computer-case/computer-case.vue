<template>
  <div>
    <h2 id="page-heading" data-cy="ComputerCaseHeading">
      <span v-text="$t('configmeApp.computerCase.home.title')" id="computer-case-heading">Computer Cases</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.computerCase.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ComputerCaseCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-computer-case"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.computerCase.home.createLabel')"> Create a new Computer Case </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && computerCases && computerCases.length === 0">
      <span v-text="$t('configmeApp.computerCase.home.notFound')">No computerCases found</span>
    </div>
    <div class="table-responsive" v-if="computerCases && computerCases.length > 0">
      <table class="table table-striped" aria-describedby="computerCases">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('configmeApp.computerCase.type')">Type</span></th>
            <th scope="row"><span v-text="$t('configmeApp.computerCase.formats')">Formats</span></th>
            <th scope="row"><span v-text="$t('configmeApp.computerCase.sizeMaxGpu')">Size Max Gpu</span></th>
            <th scope="row"><span v-text="$t('configmeApp.computerCase.sizeMaxVentirad')">Size Max Ventirad</span></th>
            <th scope="row"><span v-text="$t('configmeApp.computerCase.sizeMaxPsu')">Size Max Psu</span></th>
            <th scope="row"><span v-text="$t('configmeApp.computerCase.hardDriveSlots')">Hard Drive Slots</span></th>
            <th scope="row"><span v-text="$t('configmeApp.computerCase.frontPanelOutputs')">Front Panel Outputs</span></th>
            <th scope="row"><span v-text="$t('configmeApp.computerCase.fanIncluded')">Fan Included</span></th>
            <th scope="row"><span v-text="$t('configmeApp.computerCase.fanSlotsAvailable')">Fan Slots Available</span></th>
            <th scope="row"><span v-text="$t('configmeApp.computerCase.watercoolingCompatibility')">Watercooling Compatibility</span></th>
            <th scope="row"><span v-text="$t('configmeApp.computerCase.dimension') + ' (L, l, H)'">Dimension</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="computerCase in computerCases" :key="computerCase.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ComputerCaseView', params: { computerCaseId: computerCase.id } }">{{
                computerCase.id
              }}</router-link>
            </td>
            <td v-text="$t('configmeApp.CaseType.' + computerCase.type)">{{ computerCase.type }}</td>
            <td>{{ computerCase.formats }}</td>
            <td>{{ computerCase.sizeMaxGpu }}</td>
            <td>{{ computerCase.sizeMaxVentirad }}</td>
            <td>{{ computerCase.sizeMaxPsu }}</td>
            <td>{{ computerCase.hardDriveSlots }}</td>
            <td>{{ computerCase.frontPanelOutputs }}</td>
            <td>{{ computerCase.fanIncluded }}</td>
            <td>{{ computerCase.fanSlotsAvailable }}</td>
            <td>{{ computerCase.watercoolingCompatibility }}</td>
            <td>{{ computerCase.dimension.length }}mm, {{ computerCase.dimension.width }}mm, {{ computerCase.dimension.height }}mm</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ComputerCaseView', params: { computerCaseId: computerCase.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ComputerCaseEdit', params: { computerCaseId: computerCase.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(computerCase)"
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
        ><span id="configmeApp.computerCase.delete.question" data-cy="computerCaseDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-computerCase-heading" v-text="$t('configmeApp.computerCase.delete.question', { id: removeId })">
          Are you sure you want to delete this Computer Case?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-computerCase"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeComputerCase()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./computer-case.component.ts"></script>
