<template>
  <div>
    <h2 id="page-heading" data-cy="PsuHeading">
      <span v-text="$t('configmeApp.psu.home.title')" id="psu-heading">Psus</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.psu.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PsuCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-psu">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.psu.home.createLabel')"> Create a new Psu </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && psus && psus.length === 0">
      <span v-text="$t('configmeApp.psu.home.notFound')">No psus found</span>
    </div>
    <div class="table-responsive" v-if="psus && psus.length > 0">
      <table class="table table-striped" aria-describedby="psus">
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
            <th scope="row"><span v-text="$t('configmeApp.psu.power')">Power</span></th>
            <th scope="row"><span v-text="$t('configmeApp.psu.certification')">Certification</span></th>
            <th scope="row"><span v-text="$t('configmeApp.psu.modularity')">Modularity</span></th>
            <th scope="row"><span v-text="$t('configmeApp.psu.nbSata')">Nb Sata</span></th>
            <th scope="row"><span v-text="$t('configmeApp.psu.nbPciE')">Nb Pci E</span></th>
            <th scope="row"><span v-text="$t('configmeApp.psu.outputs')">Outputs</span></th>
            <th scope="row"><span v-text="$t('configmeApp.psu.dimension') + ' (L, l, H)'">Dimension</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="psu in psus" :key="psu.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PsuView', params: { psuId: psu.id } }">{{ psu.id }}</router-link>
            </td>
            <td>{{ psu.name }}</td>
            <td>{{ psu.price }}</td>
            <td>{{ psu.discount }}</td>
            <td>{{ psu.stock }}</td>
            <td>{{ psu.img }}</td>
            <td>{{ psu.brand }}</td>
            <td>
              <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(psu, true)" v-if="!psu.isActive">Activer</button>
              <button class="btn btn-success btn-sm" v-on:click="setActive(psu, false)" v-if="psu.isActive">Désactiver</button>
            </td>
            <td>{{ psu.power }}</td>
            <td>{{ psu.certification }}</td>
            <td v-text="$t('configmeApp.ModularityType.' + psu.modularity)">{{ psu.modularity }}</td>
            <td>{{ psu.nbSata }}</td>
            <td>{{ psu.nbPciE }}</td>
            <td>{{ psu.outputs }}</td>
            <td>{{ psu.dimension.length }}mm, {{ psu.dimension.width }}mm, {{ psu.dimension.height }}mm</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PsuView', params: { psuId: psu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PsuEdit', params: { psuId: psu.id } }" custom v-slot="{ navigate }">
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

<script lang="ts" src="./psu.component.ts"></script>
