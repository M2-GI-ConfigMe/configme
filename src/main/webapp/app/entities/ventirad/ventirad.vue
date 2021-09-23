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
            <th scope="row"><span v-text="$t('configmeApp.product.name')">Name</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.price')">Price</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.discount')">Discount</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.stock')">Stock</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.img')">Img</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.brand')">Brand</span></th>
            <th scope="row"><span v-text="$t('configmeApp.product.isActive')">Is Active</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ventirad.rangeFanSpeed')">Range Fan Speed</span></th>
            <th scope="row"><span v-text="$t('configmeApp.ventirad.sockets')">Sockets</span></th>
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
            <td>{{ ventirad.name }}</td>
            <td>{{ ventirad.price }}</td>
            <td>{{ ventirad.discount }}</td>
            <td>{{ ventirad.stock }}</td>
            <td>{{ ventirad.img }}</td>
            <td>{{ ventirad.brand }}</td>
            <td>
              <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(ventirad, true)" v-if="!ventirad.isActive">
                Activer
              </button>
              <button class="btn btn-success btn-sm" v-on:click="setActive(ventirad, false)" v-if="ventirad.isActive">Désactiver</button>
            </td>
            <td>{{ ventirad.rangeFanSpeed }}</td>
            <td>
              <p v-for="(socket, key) in ventirad.sockets" :key="key">{{ socket }}</p>
            </td>
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
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <v-pagination v-model="page" :length="pageCount"></v-pagination>
  </div>
</template>

<script lang="ts" src="./ventirad.component.ts"></script>
