<template>
  <div>
    <h2 id="page-heading" data-cy="CpuHeading">
      <span v-text="$t('configmeApp.cpu.home.title')" id="cpu-heading">Cpus</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.cpu.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CpuCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-cpu">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.cpu.home.createLabel')"> Create a new Cpu </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cpus && cpus.length === 0">
      <span v-text="$t('configmeApp.cpu.home.notFound')">No cpus found</span>
    </div>
    <div class="table-responsive" v-if="cpus && cpus.length > 0">
      <table class="table table-striped" aria-describedby="cpus">
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
            <th scope="row"><span v-text="$t('configmeApp.cpu.frequency')">Frequency</span></th>
            <th scope="row"><span v-text="$t('configmeApp.cpu.cacheL1')">Cache L 1</span></th>
            <th scope="row"><span v-text="$t('configmeApp.cpu.cacheL2')">Cache L 2</span></th>
            <th scope="row"><span v-text="$t('configmeApp.cpu.cacheL3')">Cache L 3</span></th>
            <th scope="row"><span v-text="$t('configmeApp.cpu.nbHeart')">Nb Heart</span></th>
            <th scope="row"><span v-text="$t('configmeApp.cpu.nbThread')">Nb Thread</span></th>
            <th scope="row"><span v-text="$t('configmeApp.cpu.hasVentirad')">Has Ventirad</span></th>
            <th scope="row"><span v-text="$t('configmeApp.cpu.socketType')">Socket Type</span></th>
            <th scope="row"><span v-text="$t('configmeApp.cpu.lithography')">Lithography</span></th>
            <th scope="row"><span v-text="$t('configmeApp.cpu.ramFrequencyMax')">Ram Frequency Max</span></th>
            <th scope="row"><span v-text="$t('configmeApp.cpu.consumption')">Consumption</span></th>
            <th scope="row"><span v-text="$t('configmeApp.cpu.hasGpu')">Has Gpu</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cpu in cpus" :key="cpu.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CpuView', params: { cpuId: cpu.id } }">{{ cpu.id }}</router-link>
            </td>
            <td>{{ cpu.name }}</td>
            <td>{{ cpu.price }}</td>
            <td>{{ cpu.discount }}</td>
            <td>{{ cpu.stock }}</td>
            <td>{{ cpu.img }}</td>
            <td>{{ cpu.brand }}</td>
            <td>
              <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(cpu, true)" v-if="!cpu.isActive">Activer</button>
              <button class="btn btn-success btn-sm" v-on:click="setActive(cpu, false)" v-if="cpu.isActive">Désactiver</button>
            </td>
            <td>{{ cpu.frequency }}</td>
            <td>{{ cpu.cacheL1 }}</td>
            <td>{{ cpu.cacheL2 }}</td>
            <td>{{ cpu.cacheL3 }}</td>
            <td>{{ cpu.nbHeart }}</td>
            <td>{{ cpu.nbThread }}</td>
            <td>{{ cpu.hasVentirad }}</td>
            <td v-text="$t('configmeApp.SocketType.' + cpu.socketType)">{{ cpu.socketType }}</td>
            <td>{{ cpu.lithography }}</td>
            <td>{{ cpu.ramFrequencyMax }}</td>
            <td>{{ cpu.consumption }}</td>
            <td>{{ cpu.hasGpu }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CpuView', params: { cpuId: cpu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CpuEdit', params: { cpuId: cpu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cpu)"
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
        ><span id="configmeApp.cpu.delete.question" data-cy="cpuDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-cpu-heading" v-text="$t('configmeApp.cpu.delete.question', { id: removeId })">
          Are you sure you want to delete this Cpu?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-cpu"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCpu()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <v-pagination v-model="page" :length="pageCount"></v-pagination>
  </div>
</template>

<script lang="ts" src="./cpu.component.ts"></script>
