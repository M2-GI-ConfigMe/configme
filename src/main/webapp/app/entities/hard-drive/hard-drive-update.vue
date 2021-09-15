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
            <label class="form-control-label" v-text="$t('configmeApp.product.name')" for="computer-case-name">Nom</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="computer-case-name"
              data-cy="name"
              :class="{ valid: !$v.hardDrive.name.$invalid, invalid: $v.hardDrive.name.$invalid }"
              v-model="$v.hardDrive.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.price')" for="computer-case-price">Prix</label>
            <input
              type="text"
              class="form-control"
              name="price"
              id="computer-case-price"
              data-cy="price"
              :class="{ valid: !$v.hardDrive.price.$invalid, invalid: $v.hardDrive.price.$invalid }"
              v-model="$v.hardDrive.price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.discount')" for="computer-case-discount">Réduction</label>
            <input
              type="text"
              class="form-control"
              name="discount"
              id="computer-case-discount"
              data-cy="discount"
              :class="{ valid: !$v.hardDrive.discount.$invalid, invalid: $v.hardDrive.discount.$invalid }"
              v-model="$v.hardDrive.discount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.stock')" for="computer-case-stock">Stock</label>
            <input
              type="text"
              class="form-control"
              name="stock"
              id="computer-case-stock"
              data-cy="stock"
              :class="{ valid: !$v.hardDrive.stock.$invalid, invalid: $v.hardDrive.stock.$invalid }"
              v-model="$v.hardDrive.stock.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.img')" for="computer-case-img">Image</label>
            <input
              type="text"
              class="form-control"
              name="img"
              id="computer-case-img"
              data-cy="img"
              :class="{ valid: !$v.hardDrive.img.$invalid, invalid: $v.hardDrive.img.$invalid }"
              v-model="$v.hardDrive.img.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.brand')" for="computer-case-brand">Marque</label>
            <input
              type="text"
              class="form-control"
              name="brand"
              id="computer-case-brand"
              data-cy="brand"
              :class="{ valid: !$v.hardDrive.brand.$invalid, invalid: $v.hardDrive.brand.$invalid }"
              v-model="$v.hardDrive.brand.$model"
            />
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
