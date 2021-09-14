<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.mbe.home.createOrEditLabel"
          data-cy="MbeCreateUpdateHeading"
          v-text="$t('configmeApp.mbe.home.createOrEditLabel')"
        >
          Create or edit a Mbe
        </h2>
        <div>
          <div class="form-group" v-if="mbe.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="mbe.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.name')" for="computer-case-name">Nom</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="computer-case-name"
              data-cy="name"
              :class="{ valid: !$v.mbe.name.$invalid, invalid: $v.mbe.name.$invalid }"
              v-model="$v.mbe.name.$model"
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
              :class="{ valid: !$v.mbe.price.$invalid, invalid: $v.mbe.price.$invalid }"
              v-model="$v.mbe.price.$model"
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
              :class="{ valid: !$v.mbe.discount.$invalid, invalid: $v.mbe.discount.$invalid }"
              v-model="$v.mbe.discount.$model"
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
              :class="{ valid: !$v.mbe.stock.$invalid, invalid: $v.mbe.stock.$invalid }"
              v-model="$v.mbe.stock.$model"
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
              :class="{ valid: !$v.mbe.img.$invalid, invalid: $v.mbe.img.$invalid }"
              v-model="$v.mbe.img.$model"
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
              :class="{ valid: !$v.mbe.brand.$invalid, invalid: $v.mbe.brand.$invalid }"
              v-model="$v.mbe.brand.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.mbe.socketCpu')" for="mbe-socketCpu">Socket Cpu</label>
            <select
              class="form-control"
              name="socketCpu"
              :class="{ valid: !$v.mbe.socketCpu.$invalid, invalid: $v.mbe.socketCpu.$invalid }"
              v-model="$v.mbe.socketCpu.$model"
              id="mbe-socketCpu"
              data-cy="socketCpu"
              required
            >
              <option value="AM4" v-bind:label="$t('configmeApp.SocketType.AM4')">AM4</option>
              <option value="TR4" v-bind:label="$t('configmeApp.SocketType.TR4')">TR4</option>
              <option value="LGA_1150" v-bind:label="$t('configmeApp.SocketType.LGA_1150')">LGA_1150</option>
              <option value="LGA_1151" v-bind:label="$t('configmeApp.SocketType.LGA_1151')">LGA_1151</option>
              <option value="LGA_2066" v-bind:label="$t('configmeApp.SocketType.LGA_2066')">LGA_2066</option>
              <option value="LGA_1200" v-bind:label="$t('configmeApp.SocketType.LGA_1200')">LGA_1200</option>
            </select>
            <div v-if="$v.mbe.socketCpu.$anyDirty && $v.mbe.socketCpu.$invalid">
              <small class="form-text text-danger" v-if="!$v.mbe.socketCpu.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.mbe.ramType')" for="mbe-ramType">Ram Type</label>
            <select
              class="form-control"
              name="ramType"
              :class="{ valid: !$v.mbe.ramType.$invalid, invalid: $v.mbe.ramType.$invalid }"
              v-model="$v.mbe.ramType.$model"
              id="mbe-ramType"
              data-cy="ramType"
              required
            >
              <option value="DDR3" v-bind:label="$t('configmeApp.RamType.DDR3')">DDR3</option>
              <option value="DDR4" v-bind:label="$t('configmeApp.RamType.DDR4')">DDR4</option>
              <option value="DDR5" v-bind:label="$t('configmeApp.RamType.DDR5')">DDR5</option>
            </select>
            <div v-if="$v.mbe.ramType.$anyDirty && $v.mbe.ramType.$invalid">
              <small class="form-text text-danger" v-if="!$v.mbe.ramType.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.mbe.ramFrequencyMax')" for="mbe-ramFrequencyMax"
              >Ram Frequency Max</label
            >
            <input
              type="number"
              class="form-control"
              name="ramFrequencyMax"
              id="mbe-ramFrequencyMax"
              data-cy="ramFrequencyMax"
              :class="{ valid: !$v.mbe.ramFrequencyMax.$invalid, invalid: $v.mbe.ramFrequencyMax.$invalid }"
              v-model.number="$v.mbe.ramFrequencyMax.$model"
              required
            />
            <div v-if="$v.mbe.ramFrequencyMax.$anyDirty && $v.mbe.ramFrequencyMax.$invalid">
              <small class="form-text text-danger" v-if="!$v.mbe.ramFrequencyMax.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.mbe.ramFrequencyMax.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.mbe.ramSizeMax')" for="mbe-ramSizeMax">Ram Size Max</label>
            <input
              type="number"
              class="form-control"
              name="ramSizeMax"
              id="mbe-ramSizeMax"
              data-cy="ramSizeMax"
              :class="{ valid: !$v.mbe.ramSizeMax.$invalid, invalid: $v.mbe.ramSizeMax.$invalid }"
              v-model.number="$v.mbe.ramSizeMax.$model"
              required
            />
            <div v-if="$v.mbe.ramSizeMax.$anyDirty && $v.mbe.ramSizeMax.$invalid">
              <small class="form-text text-danger" v-if="!$v.mbe.ramSizeMax.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.mbe.ramSizeMax.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.mbe.pciOutputs')" for="mbe-pciOutputs">Pci Outputs</label>
            <input
              type="text"
              class="form-control"
              name="pciOutputs"
              id="mbe-pciOutputs"
              data-cy="pciOutputs"
              :class="{ valid: !$v.mbe.pciOutputs.$invalid, invalid: $v.mbe.pciOutputs.$invalid }"
              v-model="$v.mbe.pciOutputs.$model"
              required
            />
            <div v-if="$v.mbe.pciOutputs.$anyDirty && $v.mbe.pciOutputs.$invalid">
              <small class="form-text text-danger" v-if="!$v.mbe.pciOutputs.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.mbe.displayOutput')" for="mbe-displayOutput">Display Output</label>
            <input
              type="text"
              class="form-control"
              name="displayOutput"
              id="mbe-displayOutput"
              data-cy="displayOutput"
              :class="{ valid: !$v.mbe.displayOutput.$invalid, invalid: $v.mbe.displayOutput.$invalid }"
              v-model="$v.mbe.displayOutput.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.mbe.storageOutput')" for="mbe-storageOutput">Storage Output</label>
            <input
              type="text"
              class="form-control"
              name="storageOutput"
              id="mbe-storageOutput"
              data-cy="storageOutput"
              :class="{ valid: !$v.mbe.storageOutput.$invalid, invalid: $v.mbe.storageOutput.$invalid }"
              v-model="$v.mbe.storageOutput.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.mbe.insideIO')" for="mbe-insideIO">Inside IO</label>
            <input
              type="text"
              class="form-control"
              name="insideIO"
              id="mbe-insideIO"
              data-cy="insideIO"
              :class="{ valid: !$v.mbe.insideIO.$invalid, invalid: $v.mbe.insideIO.$invalid }"
              v-model="$v.mbe.insideIO.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.mbe.backPanelOutput')" for="mbe-backPanelOutput"
              >Back Panel Output</label
            >
            <input
              type="text"
              class="form-control"
              name="backPanelOutput"
              id="mbe-backPanelOutput"
              data-cy="backPanelOutput"
              :class="{ valid: !$v.mbe.backPanelOutput.$invalid, invalid: $v.mbe.backPanelOutput.$invalid }"
              v-model="$v.mbe.backPanelOutput.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.mbe.bios')" for="mbe-bios">Bios</label>
            <input
              type="text"
              class="form-control"
              name="bios"
              id="mbe-bios"
              data-cy="bios"
              :class="{ valid: !$v.mbe.bios.$invalid, invalid: $v.mbe.bios.$invalid }"
              v-model="$v.mbe.bios.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.mbe.format')" for="mbe-format">Format</label>
            <select
              class="form-control"
              name="format"
              :class="{ valid: !$v.mbe.format.$invalid, invalid: $v.mbe.format.$invalid }"
              v-model="$v.mbe.format.$model"
              id="mbe-format"
              data-cy="format"
              required
            >
              <option value="ATX" v-bind:label="$t('configmeApp.FormatType.ATX')">ATX</option>
              <option value="FLEX_ATX" v-bind:label="$t('configmeApp.FormatType.FLEX_ATX')">FLEX_ATX</option>
              <option value="MICRO_ATX" v-bind:label="$t('configmeApp.FormatType.MICRO_ATX')">MICRO_ATX</option>
              <option value="MINI_ATX" v-bind:label="$t('configmeApp.FormatType.MINI_ATX')">MINI_ATX</option>
              <option value="EATX" v-bind:label="$t('configmeApp.FormatType.EATX')">EATX</option>
              <option value="AT" v-bind:label="$t('configmeApp.FormatType.AT')">AT</option>
            </select>
            <div v-if="$v.mbe.format.$anyDirty && $v.mbe.format.$invalid">
              <small class="form-text text-danger" v-if="!$v.mbe.format.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.mbe.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./mbe-update.component.ts"></script>
