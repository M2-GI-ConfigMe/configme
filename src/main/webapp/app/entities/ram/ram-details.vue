<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ram">
        <h2 class="jh-entity-heading" data-cy="ramDetailsHeading">
          <span v-text="$t('configmeApp.ram.detail.title')">Ram</span> {{ ram.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.ram.speed')">Speed</span>
          </dt>
          <dd>
            <span>{{ ram.speed }}</span>
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
