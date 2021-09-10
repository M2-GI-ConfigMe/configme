<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.hardDrive.home.createOrEditLabel"
          data-cy="HardDriveCreateUpdateHeading"
          v-text="$t('configmeApp.hardDrive.home.createOrEditLabel')"
        >
          Create or edit a HardDrive
        </h2>
        <div>
          <div class="form-group" v-if="hardDrive.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="hardDrive.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.hardDrive.capacity')" for="hard-drive-capacity">Capacity</label>
            <input
              type="number"
              class="form-control"
              name="capacity"
              id="hard-drive-capacity"
              data-cy="capacity"
              :class="{ valid: !$v.hardDrive.capacity.$invalid, invalid: $v.hardDrive.capacity.$invalid }"
              v-model.number="$v.hardDrive.capacity.$model"
              required
            />
            <div v-if="$v.hardDrive.capacity.$anyDirty && $v.hardDrive.capacity.$invalid">
              <small class="form-text text-danger" v-if="!$v.hardDrive.capacity.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.hardDrive.capacity.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.hardDrive.speedWrite')" for="hard-drive-speedWrite"
              >Speed Write</label
            >
            <input
              type="number"
              class="form-control"
              name="speedWrite"
              id="hard-drive-speedWrite"
              data-cy="speedWrite"
              :class="{ valid: !$v.hardDrive.speedWrite.$invalid, invalid: $v.hardDrive.speedWrite.$invalid }"
              v-model.number="$v.hardDrive.speedWrite.$model"
              required
            />
            <div v-if="$v.hardDrive.speedWrite.$anyDirty && $v.hardDrive.speedWrite.$invalid">
              <small class="form-text text-danger" v-if="!$v.hardDrive.speedWrite.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.hardDrive.speedWrite.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.hardDrive.speedRead')" for="hard-drive-speedRead">Speed Read</label>
            <input
              type="number"
              class="form-control"
              name="speedRead"
              id="hard-drive-speedRead"
              data-cy="speedRead"
              :class="{ valid: !$v.hardDrive.speedRead.$invalid, invalid: $v.hardDrive.speedRead.$invalid }"
              v-model.number="$v.hardDrive.speedRead.$model"
              required
            />
            <div v-if="$v.hardDrive.speedRead.$anyDirty && $v.hardDrive.speedRead.$invalid">
              <small class="form-text text-danger" v-if="!$v.hardDrive.speedRead.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.hardDrive.speedRead.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.hardDrive.type')" for="hard-drive-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.hardDrive.type.$invalid, invalid: $v.hardDrive.type.$invalid }"
              v-model="$v.hardDrive.type.$model"
              id="hard-drive-type"
              data-cy="type"
              required
            >
              <option value="HDD" v-bind:label="$t('configmeApp.MemoryType.HDD')">HDD</option>
              <option value="SSD" v-bind:label="$t('configmeApp.MemoryType.SSD')">SSD</option>
              <option value="M2" v-bind:label="$t('configmeApp.MemoryType.M2')">M2</option>
            </select>
            <div v-if="$v.hardDrive.type.$anyDirty && $v.hardDrive.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.hardDrive.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.hardDrive.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./hard-drive-update.component.ts"></script>
