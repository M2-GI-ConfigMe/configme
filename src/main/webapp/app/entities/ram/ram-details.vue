<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ram">
        <h2 class="jh-entity-heading titre-container-entity" data-cy="ramDetailsHeading">
          <div><span v-text="$t('configmeApp.ram.detail.title')">ComputerCase</span> {{ ram.id }}</div>
          <div>
            <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(ram, true)" v-if="!ram.isActive">Activer</button>
            <button class="btn btn-success btn-sm" v-on:click="setActive(ram, false)" v-if="ram.isActive">Désactiver</button>
          </div>
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.product.name')">Name</span>
          </dt>
          <dd>
            <span>{{ ram.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.price')">Price</span>
          </dt>
          <dd>
            <span>{{ ram.price }} €</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.discount')">Discount</span>
          </dt>
          <dd>
            <span>{{ Math.floor((1 - ram.discount) * 100) }}%</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.stock')">Stock</span>
          </dt>
          <dd>
            <span>{{ ram.stock }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.img')">Image</span>
          </dt>
          <dd>
            <span><img :src="ram.img" alt="photot de l'article" /></span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.brand')">Marque</span>
          </dt>
          <dd>
            <span>{{ ram.brand }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.ram.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('configmeApp.RamType.' + ram.type)">{{ ram.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.ram.frequency')">Frequency</span>
          </dt>
          <dd>
            <span>{{ ram.frequency }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.ram.unitSize')">Unit Size</span>
          </dt>
          <dd>
            <span>{{ ram.unitSize }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.ram.quantity')">Quantity</span>
          </dt>
          <dd>
            <span>{{ ram.quantity }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.ram.cas')">Cas</span>
          </dt>
          <dd>
            <span>{{ ram.cas }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="ram.id" :to="{ name: 'RamEdit', params: { ramId: ram.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ram-details.component.ts"></script>
