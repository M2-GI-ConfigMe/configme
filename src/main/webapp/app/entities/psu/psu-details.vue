<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="psu">
        <h2 class="jh-entity-heading" data-cy="psuDetailsHeading">
          <span v-text="$t('configmeApp.psu.detail.title')">Psu</span> {{ psu.id }}
        </h2>
        <dl class="row jh-entity-details">
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
