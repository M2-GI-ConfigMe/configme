<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="hardDrive">
        <h2 class="jh-entity-heading" data-cy="hardDriveDetailsHeading">
          <span v-text="$t('configmeApp.hardDrive.detail.title')">HardDrive</span> {{ hardDrive.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.hardDrive.capacity')">Capacity</span>
          </dt>
          <dd>
            <span>{{ hardDrive.capacity }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.hardDrive.speedWrite')">Speed Write</span>
          </dt>
          <dd>
            <span>{{ hardDrive.speedWrite }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.hardDrive.speedRead')">Speed Read</span>
          </dt>
          <dd>
            <span>{{ hardDrive.speedRead }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.hardDrive.type')">Type</span>
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
