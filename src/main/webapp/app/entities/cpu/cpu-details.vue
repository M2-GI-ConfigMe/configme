<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cpu">
        <h2 class="jh-entity-heading titre-container-entity" data-cy="cpuDetailsHeading">
          <div><span v-text="$t('configmeApp.cpu.detail.title')">Cpu</span> {{ cpu.id }}</div>
          <div>
            <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(cpu, true)" v-if="!cpu.isActive">Activer</button>
            <button class="btn btn-success btn-sm" v-on:click="setActive(cpu, false)" v-if="cpu.isActive">Désactiver</button>
          </div>
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.product.name')">Name</span>
          </dt>
          <dd>
            <span>{{ cpu.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.price')">Price</span>
          </dt>
          <dd>
            <span>{{ cpu.price }} €</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.discount')">Discount</span>
          </dt>
          <dd>
            <span>{{ Math.floor((1 - cpu.discount) * 100) }}%</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.stock')">Stock</span>
          </dt>
          <dd>
            <span>{{ cpu.stock }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.img')">Image</span>
          </dt>
          <dd>
            <span><img :src="cpu.img" alt="photot de l'article" /></span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.brand')">Marque</span>
          </dt>
          <dd>
            <span>{{ cpu.brand }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.frequency')">Frequency</span>
          </dt>
          <dd>
            <span>{{ cpu.frequency }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.cacheL1')">Cache L 1</span>
          </dt>
          <dd>
            <span>{{ cpu.cacheL1 }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.cacheL2')">Cache L 2</span>
          </dt>
          <dd>
            <span>{{ cpu.cacheL2 }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.cacheL3')">Cache L 3</span>
          </dt>
          <dd>
            <span>{{ cpu.cacheL3 }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.nbHeart')">Nb Heart</span>
          </dt>
          <dd>
            <span>{{ cpu.nbHeart }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.nbThread')">Nb Thread</span>
          </dt>
          <dd>
            <span>{{ cpu.nbThread }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.hasVentirad')">Has Ventirad</span>
          </dt>
          <dd>
            <span>{{ cpu.hasVentirad }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.socketType')">Socket Type</span>
          </dt>
          <dd>
            <span v-text="$t('configmeApp.SocketType.' + cpu.socketType)">{{ cpu.socketType }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.lithography')">Lithography</span>
          </dt>
          <dd>
            <span>{{ cpu.lithography }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.ramFrequencyMax')">Ram Frequency Max</span>
          </dt>
          <dd>
            <span>{{ cpu.ramFrequencyMax }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.consumption')">Consumption</span>
          </dt>
          <dd>
            <span>{{ cpu.consumption }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.cpu.hasGpu')">Has Gpu</span>
          </dt>
          <dd>
            <span>{{ cpu.hasGpu }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="cpu.id" :to="{ name: 'CpuEdit', params: { cpuId: cpu.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cpu-details.component.ts"></script>
