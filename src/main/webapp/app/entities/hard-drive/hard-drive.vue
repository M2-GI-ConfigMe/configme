<template>
  <div>
    <h2 id="page-heading" data-cy="HardDriveHeading">
      <span v-text="$t('configmeApp.hd.home.title')" id="hard-drive-heading">Hard Drives</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('configmeApp.hd.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'HardDriveCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-hard-drive"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('configmeApp.hd.home.createLabel')"> Create a new Hard Drive </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && hardDrives && hardDrives.length === 0">
      <span v-text="$t('configmeApp.hd.home.notFound')">No hardDrives found</span>
    </div>
    <div class="table-responsive" v-if="hardDrives && hardDrives.length > 0">
      <table class="table table-striped" aria-describedby="hardDrives">
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
            <th scope="row"><span v-text="$t('configmeApp.hd.capacity')">Capacity</span></th>
            <th scope="row"><span v-text="$t('configmeApp.hd.speedWrite')">Speed Write</span></th>
            <th scope="row"><span v-text="$t('configmeApp.hd.speedRead')">Speed Read</span></th>
            <th scope="row"><span v-text="$t('configmeApp.hd.type')">Type</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="hardDrive in hardDrives" :key="hardDrive.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'HardDriveView', params: { hardDriveId: hardDrive.id } }">{{ hardDrive.id }}</router-link>
            </td>
            <td>{{ hardDrive.name }}</td>
            <td>{{ hardDrive.price }}</td>
            <td>{{ hardDrive.discount }}</td>
            <td>{{ hardDrive.stock }}</td>
            <td>{{ hardDrive.img }}</td>
            <td>{{ hardDrive.brand }}</td>
            <td>
              <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(hardDrive, true)" v-if="!hardDrive.isActive">
                Activer
              </button>
              <button class="btn btn-success btn-sm" v-on:click="setActive(hardDrive, false)" v-if="hardDrive.isActive">Désactiver</button>
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
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <v-pagination v-model="page" :length="pageCount"></v-pagination>
  </div>
</template>

<script lang="ts" src="./hard-drive.component.ts"></script>
