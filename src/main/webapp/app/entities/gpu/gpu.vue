<template>
  <div>
    <h2 id="page-heading" data-cy="GpuHeading">
      <span v-text="$t('configmeApp.gpu.home.title')" id="gpu-heading">Gpus</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.gpu.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'GpuCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-gpu">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.gpu.home.createLabel')"> Create a new Gpu </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && gpus && gpus.length === 0">
      <span v-text="$t('configmeApp.gpu.home.notFound')">No gpus found</span>
    </div>
    <div class="table-responsive" v-if="gpus && gpus.length > 0">
      <table class="table table-striped" aria-describedby="gpus">
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
            <th scope="row"><span v-text="$t('configmeApp.gpu.frequency')">Frequency</span></th>
            <th scope="row"><span v-text="$t('configmeApp.gpu.memory')">Memory</span></th>
            <th scope="row"><span v-text="$t('configmeApp.gpu.consumption')">Consumption</span></th>
            <th scope="row"><span v-text="$t('configmeApp.gpu.clockSpeed')">Clock Speed</span></th>
            <th scope="row"><span v-text="$t('configmeApp.gpu.lithography')">Lithography</span></th>
            <th scope="row"><span v-text="$t('configmeApp.gpu.output')">Output</span></th>
            <th scope="row"><span v-text="$t('configmeApp.gpu.inputPower')">Input Power</span></th>
            <th scope="row"><span v-text="$t('configmeApp.gpu.bus')">Bus</span></th>
            <th scope="row"><span v-text="$t('configmeApp.gpu.dimension') + ' (L, l, H)'">Dimension</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="gpu in gpus" :key="gpu.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'GpuView', params: { gpuId: gpu.id } }">{{ gpu.id }}</router-link>
            </td>
            <td>{{ gpu.name }}</td>
            <td>{{ gpu.price }}</td>
            <td>{{ gpu.discount }}</td>
            <td>{{ gpu.stock }}</td>
            <td>{{ gpu.img }}</td>
            <td>{{ gpu.brand }}</td>
            <td>
              <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(gpu, true)" v-if="!gpu.isActive">Activer</button>
              <button class="btn btn-success btn-sm" v-on:click="setActive(gpu, false)" v-if="gpu.isActive">Désactiver</button>
            </td>
            <td>{{ gpu.frequency }}</td>
            <td>{{ gpu.memory }}</td>
            <td>{{ gpu.consumption }}</td>
            <td>{{ gpu.clockSpeed }}</td>
            <td>{{ gpu.lithography }}</td>
            <td>{{ gpu.output }}</td>
            <td>{{ gpu.inputPower }}</td>
            <td v-text="$t('configmeApp.BusType.' + gpu.bus)">{{ gpu.bus }}</td>
            <td>{{ gpu.dimension.length }}mm, {{ gpu.dimension.width }}mm, {{ gpu.dimension.height }}mm</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'GpuView', params: { gpuId: gpu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'GpuEdit', params: { gpuId: gpu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <v-pagination v-model="page" :length="pageCount"></v-pagination>
  </div>
</template>

<script lang="ts" src="./gpu.component.ts"></script>
