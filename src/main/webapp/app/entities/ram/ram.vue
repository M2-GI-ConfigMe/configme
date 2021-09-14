<template>
  <div>
    <h2 id="page-heading" data-cy="RamHeading">
      <span v-text="$t('configmeApp.ram.home.title')" id="ram-heading">Rams</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.ram.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RamCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-ram">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.ram.home.createLabel')"> Create a new Ram </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rams && rams.length === 0">
      <span v-text="$t('configmeApp.ram.home.notFound')">No rams found</span>
    </div>
    <div class="table-responsive" v-if="rams && rams.length > 0">
      <table class="table table-striped" aria-describedby="rams">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.name')">Name</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.price')">Price</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.discount')">Discount</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.stock')">Stock</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.img')">Img</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.brand')">Brand</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.isActive')">Is Active</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ram.speed')">Speed</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ram.type')">Type</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ram.frequency')">Frequency</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ram.unitSize')">Unit Size</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ram.quantity')">Quantity</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ram.cas')">Cas</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ram in rams" :key="ram.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RamView', params: { ramId: ram.id } }">{{ ram.id }}</router-link>
            </td>
            <td>{{ ram.name }}</td>
            <td>{{ ram.price }}</td>
            <td>{{ ram.discount }}</td>
            <td>{{ ram.stock }}</td>
            <td>{{ ram.img }}</td>
            <td>{{ ram.brand }}</td>
            <td>
              <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(ram, true)" v-if="!ram.isActive">Activer</button>
              <button class="btn btn-success btn-sm" v-on:click="setActive(ram, false)" v-if="ram.isActive">Désactiver</button>
            </td>
            <td>{{ ram.speed }}</td>
            <td v-text="$t('configmeApp.RamType.' + ram.type)">{{ ram.type }}</td>
            <td>{{ ram.frequency }}</td>
            <td>{{ ram.unitSize }}</td>
            <td>{{ ram.quantity }}</td>
            <td>{{ ram.cas }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RamView', params: { ramId: ram.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RamEdit', params: { ramId: ram.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(ram)"
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
        ><span id="configmeApp.ram.delete.question" data-cy="ramDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-ram-heading" v-text="$t('configmeApp.ram.delete.question', { id: removeId })">
          Are you sure you want to delete this Ram?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-ram"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRam()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./ram.component.ts"></script>
