<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="psu">
        <h2 class="jh-entity-heading titre-container-entity" data-cy="psuDetailsHeading">
          <div><span v-text="$t('configmeApp.psu.detail.title')">ComputerCase</span> {{ psu.id }}</div>
          <div>
            <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(psu, true)" v-if="!psu.isActive">Activer</button>
            <button class="btn btn-success btn-sm" v-on:click="setActive(psu, false)" v-if="psu.isActive">Désactiver</button>
          </div>
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.product.name')">Name</span>
          </dt>
          <dd>
            <span>{{ psu.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.price')">Price</span>
          </dt>
          <dd>
            <span>{{ psu.price }} €</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.discount')">Discount</span>
          </dt>
          <dd>
            <span>{{ Math.floor((1 - psu.discount) * 100) }}%</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.stock')">Stock</span>
          </dt>
          <dd>
            <span>{{ psu.stock }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.img')">Image</span>
          </dt>
          <dd>
            <span><img :src="psu.img" alt="photot de l'article" /></span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.brand')">Marque</span>
          </dt>
          <dd>
            <span>{{ psu.brand }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.psu.power')">Power</span>
          </dt>
          <dd>
            <span>{{ psu.power }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.psu.certification')">Certification</span>
          </dt>
          <dd>
            <span>{{ psu.certification }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.psu.modularity')">Modularity</span>
          </dt>
          <dd>
            <span v-text="$t('configmeApp.ModularityType.' + psu.modularity)">{{ psu.modularity }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.psu.nbSata')">Nb Sata</span>
          </dt>
          <dd>
            <span>{{ psu.nbSata }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.psu.nbPciE')">Nb Pci E</span>
          </dt>
          <dd>
            <span>{{ psu.nbPciE }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.psu.outputs')">Outputs</span>
          </dt>
          <dd>
            <span>{{ psu.outputs }}</span>
          </dd>
          <template v-if="psu.dimension">
            <dt>
              <span v-text="$t('configmeApp.dimension.height')">Height</span>
            </dt>
            <dd>
              <span>{{ psu.dimension.height }} mm</span>
            </dd>
            <dt>
              <span v-text="$t('configmeApp.dimension.width')">Width</span>
            </dt>
            <dd>
              <span>{{ psu.dimension.width }} mm</span>
            </dd>
            <dt>
              <span v-text="$t('configmeApp.dimension.length')">Length</span>
            </dt>
            <dd>
              <span>{{ psu.dimension.length }} mm</span>
            </dd>
          </template>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="psu.id" :to="{ name: 'PsuEdit', params: { psuId: psu.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./psu-details.component.ts"></script>
