<template>
  <div>
    <h2 id="page-heading" data-cy="ClientConfigHeading">
      <span v-text="$t('configmeApp.clientConfig.home.title')" id="client-config-heading">Client Configs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.clientConfig.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ClientConfigCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-client-config"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.clientConfig.home.createLabel')"> Create a new Client Config </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && clientConfigs && clientConfigs.length === 0">
      <span v-text="$t('configmeApp.clientConfig.home.notFound')">No clientConfigs found</span>
    </div>
    <div class="table-responsive" v-if="clientConfigs && clientConfigs.length > 0">
      <table class="table table-striped" aria-describedby="clientConfigs">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.tags')">Tags</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.name')">Name</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.description')">Description</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.isComlete')">Is Comlete</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.researchKey')">Research Key</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.cpuPrice')">Cpu Price</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.gpuPrice')">Gpu Price</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.ram1Price')">Ram 1 Price</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.ram2Price')">Ram 2 Price</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.psuPrice')">Psu Price</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.computerCasePrice')">Computer Case Price</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.ventiradPrice')">Ventirad Price</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.hd1Price')">Hd 1 Price</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.hd2Price')">Hd 2 Price</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.cpu')">Cpu</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.gpu')">Gpu</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.psu')">Psu</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.ventirad')">Ventirad</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.mbe')">Mbe</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.computerCase')">Computer Case</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.deadMemory1')">Dead Memory 1</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.deadMemory2')">Dead Memory 2</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.ram1')">Ram 1</span></th>
            <th scope="row"><span v-text="$t('configmeApp.clientConfig.ram2')">Ram 2</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="clientConfig in clientConfigs" :key="clientConfig.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ClientConfigView', params: { clientConfigId: clientConfig.id } }">{{
                clientConfig.id
              }}</router-link>
            </td>
            <td>{{ clientConfig.tags }}</td>
            <td>{{ clientConfig.name }}</td>
            <td>{{ clientConfig.description }}</td>
            <td>{{ clientConfig.isComlete }}</td>
            <td>{{ clientConfig.researchKey }}</td>
            <td>{{ clientConfig.cpuPrice }}</td>
            <td>{{ clientConfig.gpuPrice }}</td>
            <td>{{ clientConfig.ram1Price }}</td>
            <td>{{ clientConfig.ram2Price }}</td>
            <td>{{ clientConfig.psuPrice }}</td>
            <td>{{ clientConfig.computerCasePrice }}</td>
            <td>{{ clientConfig.ventiradPrice }}</td>
            <td>{{ clientConfig.hd1Price }}</td>
            <td>{{ clientConfig.hd2Price }}</td>
            <td>
              <div v-if="clientConfig.cpu">
                <router-link :to="{ name: 'CpuView', params: { cpuId: clientConfig.cpu.id } }">{{ clientConfig.cpu.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="clientConfig.gpu">
                <router-link :to="{ name: 'GpuView', params: { gpuId: clientConfig.gpu.id } }">{{ clientConfig.gpu.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="clientConfig.psu">
                <router-link :to="{ name: 'PsuView', params: { psuId: clientConfig.psu.id } }">{{ clientConfig.psu.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="clientConfig.ventirad">
                <router-link :to="{ name: 'VentiradView', params: { ventiradId: clientConfig.ventirad.id } }">{{
                  clientConfig.ventirad.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="clientConfig.mbe">
                <router-link :to="{ name: 'MbeView', params: { mbeId: clientConfig.mbe.id } }">{{ clientConfig.mbe.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="clientConfig.computerCase">
                <router-link :to="{ name: 'ComputerCaseView', params: { computerCaseId: clientConfig.computerCase.id } }">{{
                  clientConfig.computerCase.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="clientConfig.deadMemory1">
                <router-link :to="{ name: 'HardDriveView', params: { hardDriveId: clientConfig.deadMemory1.id } }">{{
                  clientConfig.deadMemory1.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="clientConfig.deadMemory2">
                <router-link :to="{ name: 'HardDriveView', params: { hardDriveId: clientConfig.deadMemory2.id } }">{{
                  clientConfig.deadMemory2.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="clientConfig.ram1">
                <router-link :to="{ name: 'RamView', params: { ramId: clientConfig.ram1.id } }">{{ clientConfig.ram1.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="clientConfig.ram2">
                <router-link :to="{ name: 'RamView', params: { ramId: clientConfig.ram2.id } }">{{ clientConfig.ram2.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ClientConfigView', params: { clientConfigId: clientConfig.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ClientConfigEdit', params: { clientConfigId: clientConfig.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(clientConfig)"
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
        ><span id="configmeApp.clientConfig.delete.question" data-cy="clientConfigDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-clientConfig-heading" v-text="$t('configmeApp.clientConfig.delete.question', { id: removeId })">
          Are you sure you want to delete this Client Config?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-clientConfig"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeClientConfig()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./client-config.component.ts"></script>
