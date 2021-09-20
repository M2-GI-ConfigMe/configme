<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="hardDrive">
        <h2 class="jh-entity-heading titre-container-entity" data-cy="hardDriveDetailsHeading">
          <div><span v-text="$t('configmeApp.hd.detail.title')">ComputerCase</span> {{ hardDrive.id }}</div>
          <div>
            <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(hardDrive, true)" v-if="!hardDrive.isActive">
              Activer
            </button>
            <button class="btn btn-success btn-sm" v-on:click="setActive(hardDrive, false)" v-if="hardDrive.isActive">Désactiver</button>
          </div>
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.product.name')">Name</span>
          </dt>
          <dd>
            <span>{{ hardDrive.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.price')">Price</span>
          </dt>
          <dd>
            <span>{{ hardDrive.price }} €</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.discount')">Discount</span>
          </dt>
          <dd>
            <span>{{ Math.floor((1 - hardDrive.discount) * 100) }}%</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.stock')">Stock</span>
          </dt>
          <dd>
            <span>{{ hardDrive.stock }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.img')">Image</span>
          </dt>
          <dd>
            <span><img :src="hardDrive.img" alt="photot de l'article" /></span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.brand')">Marque</span>
          </dt>
          <dd>
            <span>{{ hardDrive.brand }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.hd.capacity')">Capacity</span>
          </dt>
          <dd>
            <span>{{ hardDrive.capacity }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.hd.speedWrite')">Speed Write</span>
          </dt>
          <dd>
            <span>{{ hardDrive.speedWrite }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.hd.speedRead')">Speed Read</span>
          </dt>
          <dd>
            <span>{{ hardDrive.speedRead }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.hd.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('configmeApp.MemoryType.' + hardDrive.type)">{{ hardDrive.type }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="hardDrive.id"
          :to="{ name: 'HardDriveEdit', params: { hardDriveId: hardDrive.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./hard-drive-details.component.ts"></script>
